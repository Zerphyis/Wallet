package dev.Zerphyis.wallet.Application.UseCases.Transacao;

import dev.Zerphyis.wallet.Application.Exceptions.SaldoInsuficienteException;
import dev.Zerphyis.wallet.Application.Exceptions.ValorInvalidoException;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;
import dev.Zerphyis.wallet.Domain.Repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealizarTransacaoUseCaseTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private RealizarTransacaoUseCase useCase;

    private UUID idCliente;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        idCliente = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setId(idCliente);
    }

    @Nested
    @DisplayName("Caminho Infeliz (Exceções e Falhas de Validação)")
    class CaminhoInfelizTests {

        @Test
        @DisplayName("Deve lançar ValorInvalidoException quando valor da transação for nulo")
        void deveLancarExcecaoQuandoValorNulo() {
            Transacao transacao = new Transacao();
            transacao.setCliente(cliente);
            transacao.setValor(null);
            transacao.setTipo(TipoTransacao.DEPOSIT);

            assertThrows(ValorInvalidoException.class, () -> useCase.executar(transacao));
            verify(repository, never()).salvarTransacao(any());
        }

        @Test
        @DisplayName("Deve lançar ValorInvalidoException quando valor da transação for zero")
        void deveLancarExcecaoQuandoValorZero() {
            Transacao transacao = new Transacao();
            transacao.setCliente(cliente);
            transacao.setValor(BigDecimal.ZERO);
            transacao.setTipo(TipoTransacao.DEPOSIT);

            assertThrows(ValorInvalidoException.class, () -> useCase.executar(transacao));
            verify(repository, never()).salvarTransacao(any());
        }

        @Test
        @DisplayName("Deve lançar ValorInvalidoException quando valor for negativo")
        void deveLancarExcecaoQuandoValorNegativo() {
            Transacao transacao = new Transacao();
            transacao.setCliente(cliente);
            transacao.setValor(new BigDecimal("-50.00"));
            transacao.setTipo(TipoTransacao.DEPOSIT);

            assertThrows(ValorInvalidoException.class, () -> useCase.executar(transacao));
            verify(repository, never()).salvarTransacao(any());
        }

        @Test
        @DisplayName("Deve lançar SaldoInsuficienteException quando tentar realizar saque com saldo insuficiente")
        void deveLancarExcecaoQuandoSaldoInsuficiente() {
            Transacao depositoExistente = new Transacao();
            depositoExistente.setValor(new BigDecimal("50.00"));
            depositoExistente.setTipo(TipoTransacao.DEPOSIT);

            when(repository.buscarPorCliente(idCliente)).thenReturn(List.of(depositoExistente));

            Transacao saqueTentativa = new Transacao();
            saqueTentativa.setCliente(cliente);
            saqueTentativa.setValor(new BigDecimal("100.00"));
            saqueTentativa.setTipo(TipoTransacao.WITHDRAWAL);

            assertThrows(SaldoInsuficienteException.class, () -> useCase.executar(saqueTentativa));
            verify(repository, times(1)).buscarPorCliente(idCliente);
            verify(repository, never()).salvarTransacao(any());
        }
    }

    @Nested
    @DisplayName("Caminho Feliz (Sucesso na Execução)")
    class CaminhoFelizTests {

        @Test
        @DisplayName("Deve realizar depósito com sucesso quando saldo for zero")
        void deveRealizarDepositoComSucesso() {
            when(repository.buscarPorCliente(idCliente)).thenReturn(Collections.emptyList());

            Transacao deposito = new Transacao();
            deposito.setCliente(cliente);
            deposito.setValor(new BigDecimal("100.00"));
            deposito.setTipo(TipoTransacao.DEPOSIT);

            when(repository.salvarTransacao(any(Transacao.class))).thenAnswer(i -> i.getArgument(0));

            Transacao resultado = useCase.executar(deposito);

            assertNotNull(resultado);
            assertEquals(new BigDecimal("100.00"), resultado.getValor());
            verify(repository, times(1)).salvarTransacao(deposito);
        }

        @Test
        @DisplayName("Deve realizar saque com sucesso quando houver saldo suficiente")
        void deveRealizarSaqueComSucesso() {
            Transacao depositoExistente = new Transacao();
            depositoExistente.setValor(new BigDecimal("200.00"));
            depositoExistente.setTipo(TipoTransacao.DEPOSIT);

            when(repository.buscarPorCliente(idCliente)).thenReturn(List.of(depositoExistente));

            Transacao saque = new Transacao();
            saque.setCliente(cliente);
            saque.setValor(new BigDecimal("150.00"));
            saque.setTipo(TipoTransacao.WITHDRAWAL);

            when(repository.salvarTransacao(any(Transacao.class))).thenAnswer(i -> i.getArgument(0));

            Transacao resultado = useCase.executar(saque);

            assertNotNull(resultado);
            assertEquals(new BigDecimal("150.00"), resultado.getValor());
            verify(repository, times(1)).salvarTransacao(saque);
        }

        @Test
        @DisplayName("Deve atribuir a data atual automaticamente se a transação for recebida com data nula")
        void devePreencherDataAtualQuandoNula() {
            when(repository.buscarPorCliente(idCliente)).thenReturn(Collections.emptyList());

            Transacao deposito = new Transacao();
            deposito.setCliente(cliente);
            deposito.setValor(new BigDecimal("50.00"));
            deposito.setTipo(TipoTransacao.DEPOSIT);
            deposito.setData(null);

            when(repository.salvarTransacao(any(Transacao.class))).thenAnswer(i -> i.getArgument(0));

            Transacao resultado = useCase.executar(deposito);

            assertNotNull(resultado.getData());
            assertTrue(resultado.getData().isBefore(LocalDateTime.now().plusSeconds(1)));
            verify(repository, times(1)).salvarTransacao(deposito);
        }

        @Test
        @DisplayName("Deve manter a data informada se a transação já contiver data definida")
        void deveManterDataQuandoJaInformada() {
            when(repository.buscarPorCliente(idCliente)).thenReturn(Collections.emptyList());

            LocalDateTime dataOriginal = LocalDateTime.of(2026, 10, 20, 10, 0);
            Transacao deposito = new Transacao();
            deposito.setCliente(cliente);
            deposito.setValor(new BigDecimal("50.00"));
            deposito.setTipo(TipoTransacao.DEPOSIT);
            deposito.setData(dataOriginal);

            when(repository.salvarTransacao(any(Transacao.class))).thenAnswer(i -> i.getArgument(0));

            Transacao resultado = useCase.executar(deposito);

            assertEquals(dataOriginal, resultado.getData());
            verify(repository, times(1)).salvarTransacao(deposito);
        }
    }
}
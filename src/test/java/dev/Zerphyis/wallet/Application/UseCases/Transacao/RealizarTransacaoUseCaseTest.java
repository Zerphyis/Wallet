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


}
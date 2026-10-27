package dev.Zerphyis.wallet.Application.UseCases.Transacao;

import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarExtratoESaldeUseCaseTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private ConsultarExtratoESaldoUseCase useCase;

    private UUID idCliente;

    @BeforeEach
    void setUp() {
        idCliente = UUID.randomUUID();
    }

    @Nested
    @DisplayName("Caminhos do método executar (Extrato Completo)")
    class ExecutarExtratoTests {

        @Test
        @DisplayName("Deve retornar extrato com saldo consolidado correto quando houver depósitos e saques")
        void deveRetornarExtratoComSaldoConsolidadoCorreto() {
            Transacao deposito = criarTransacao(new BigDecimal("200.00"), TipoTransacao.DEPOSIT);
            Transacao saque = criarTransacao(new BigDecimal("50.00"), TipoTransacao.WITHDRAWAL);

            when(repository.buscarPorCliente(idCliente)).thenReturn(List.of(deposito, saque));

            ExtratoResponse response = useCase.executar(idCliente);

            assertNotNull(response);
            assertEquals(idCliente, response.idCliente());
            assertEquals(new BigDecimal("150.00"), response.saldoConsolidado());
            assertEquals(2, response.transacoes().size());

            verify(repository, times(1)).buscarPorCliente(idCliente);
        }



    private Transacao criarTransacao(BigDecimal valor, TipoTransacao tipo) {
        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        return transacao;
    }
}
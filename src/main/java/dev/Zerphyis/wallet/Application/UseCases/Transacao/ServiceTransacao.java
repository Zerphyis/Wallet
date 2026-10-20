package dev.Zerphyis.wallet.Application.UseCases.Transacao;

import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponse;
import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.ConsultarExtratoESaldoInterfaceCase;
import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.RealizarTransacaoInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceTransacao {

    private final RealizarTransacaoInterfaceCase realizarTransacaoUseCase;
    private final ConsultarExtratoESaldoInterfaceCase consultarExtratoESaldoUseCase;

    public ServiceTransacao(
            RealizarTransacaoInterfaceCase realizarTransacaoUseCase,
            ConsultarExtratoESaldoInterfaceCase consultarExtratoESaldoUseCase
    ) {
        this.realizarTransacaoUseCase = realizarTransacaoUseCase;
        this.consultarExtratoESaldoUseCase = consultarExtratoESaldoUseCase;
    }

    public Transacao realizarTransacao(Transacao transacao) {
        return realizarTransacaoUseCase.executar(transacao);
    }

    public ExtratoResponse consultarExtrato(UUID idCliente) {
        return consultarExtratoESaldoUseCase.executar(idCliente);
    }

    public BigDecimal consultarSaldo(UUID idCliente) {
        return consultarExtratoESaldoUseCase.consultarSaldoApenas(idCliente);
    }
}
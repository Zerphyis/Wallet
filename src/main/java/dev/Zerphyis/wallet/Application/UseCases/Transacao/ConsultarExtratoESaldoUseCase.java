package dev.Zerphyis.wallet.Application.UseCases.Transacao;

import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponse;
import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.ConsultarExtratoESaldoInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;
import dev.Zerphyis.wallet.Domain.Repository.TransacaoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ConsultarExtratoESaldoUseCase implements ConsultarExtratoESaldoInterfaceCase {

    private final TransacaoRepository repository;

    public ConsultarExtratoESaldoUseCase(TransacaoRepository repository) {
        this.repository = repository;
    }

    public ExtratoResponse executar(UUID idCliente) {
        List<Transacao> transacoes = repository.buscarPorCliente(idCliente);
        BigDecimal saldoConsolidado = calcularSaldoConsolidado(transacoes);

        return new ExtratoResponse(idCliente, saldoConsolidado, transacoes);
    }

    public BigDecimal consultarSaldoApenas(UUID idCliente) {
        List<Transacao> transacoes = repository.buscarPorCliente(idCliente);
        return calcularSaldoConsolidado(transacoes);
    }

    private BigDecimal calcularSaldoConsolidado(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(t -> isDeposito(t.getTipo()) ? t.getValor() : t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isDeposito(TipoTransacao tipo) {
        return tipo == TipoTransacao.DEPOSIT || tipo.name().equals("DEPOSITO");
    }

}

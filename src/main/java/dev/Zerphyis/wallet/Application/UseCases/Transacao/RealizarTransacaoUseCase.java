package dev.Zerphyis.wallet.Application.UseCases.Transacao;

import dev.Zerphyis.wallet.Application.Exceptions.SaldoInsuficienteException;
import dev.Zerphyis.wallet.Application.Exceptions.ValorInvalidoException;
import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.RealizarTransacaoInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;
import dev.Zerphyis.wallet.Domain.Repository.TransacaoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RealizarTransacaoUseCase implements RealizarTransacaoInterfaceCase {
    private final TransacaoRepository repository;

    public RealizarTransacaoUseCase(TransacaoRepository repository) {
        this.repository = repository;
    }

    public Transacao executar(Transacao transacao) {
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException();
        }

        if (transacao.getData() == null) {
            transacao.setData(LocalDateTime.now());
        }

        List<Transacao> historico = repository.buscarPorCliente(transacao.getCliente().getId());
        BigDecimal saldoAtual = calcularSaldoConsolidado(historico);

        if (isSaque(transacao.getTipo())) {
            BigDecimal saldoResultante = saldoAtual.subtract(transacao.getValor());
            if (saldoResultante.compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoInsuficienteException();
            }
        }

        return repository.salvarTransacao(transacao);
    }

    private BigDecimal calcularSaldoConsolidado(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(t -> isDeposito(t.getTipo()) ? t.getValor() : t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isDeposito(TipoTransacao tipo) {
        return tipo == TipoTransacao.DEPOSIT || tipo.name().equals("DEPOSITO");
    }

    private boolean isSaque(TipoTransacao tipo) {
        return tipo == TipoTransacao.WITHDRAWAL || tipo.name().equals("SAQUE");
    }
}

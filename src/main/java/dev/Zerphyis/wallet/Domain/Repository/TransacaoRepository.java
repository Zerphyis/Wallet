package dev.Zerphyis.wallet.Domain.Repository;

import dev.Zerphyis.wallet.Domain.Entitys.Transacao;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository {
    Transacao salvarTransacao(Transacao transacao);
    List<Transacao> buscarPorCliente(UUID idCliente);
}

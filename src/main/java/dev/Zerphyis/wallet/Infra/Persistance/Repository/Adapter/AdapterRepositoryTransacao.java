package dev.Zerphyis.wallet.Infra.Persistance.Repository.Adapter;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;
import dev.Zerphyis.wallet.Domain.Repository.TransacaoRepository;
import dev.Zerphyis.wallet.Infra.Persistance.EntityJpa.ClienteJpa;
import dev.Zerphyis.wallet.Infra.Persistance.EntityJpa.TransacaoJpa;
import dev.Zerphyis.wallet.Infra.Persistance.Repository.TransacaoRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AdapterRepositoryTransacao implements TransacaoRepository {
    private final TransacaoRepositoryJpa transacaoRepositoryJpa;

    public AdapterRepositoryTransacao(TransacaoRepositoryJpa transacaoRepositoryJpa) {
        this.transacaoRepositoryJpa = transacaoRepositoryJpa;
    }

    @Override
    public Transacao salvarTransacao(Transacao transacao) {
        TransacaoJpa jpaEntity = toJpa(transacao);
        TransacaoJpa jpaSalvo = transacaoRepositoryJpa.save(jpaEntity);
        return toDomain(jpaSalvo);
    }

    @Override
    public List<Transacao> buscarPorCliente(UUID idCliente) {
        List<TransacaoJpa> transacoesJpa = transacaoRepositoryJpa.findByClienteId(idCliente);
        return transacoesJpa.stream()
                .map(this::toDomain)
                .toList();
    }

    private TransacaoJpa toJpa(Transacao transacao) {
        TransacaoJpa jpa = new TransacaoJpa();
        jpa.setId(transacao.getId());

        if (transacao.getCliente() != null) {
            ClienteJpa clienteJpa = new ClienteJpa();
            clienteJpa.setId(transacao.getCliente().getId());
            jpa.setCliente(clienteJpa);
        }

        jpa.setValor(transacao.getValor());
        jpa.setTipo(transacao.getTipo());
        jpa.setData(transacao.getData());

        return jpa;
    }

    private Transacao toDomain(TransacaoJpa jpa) {
        Transacao transacao = new Transacao();
        transacao.setId(jpa.getId());

        if (jpa.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(jpa.getCliente().getId());
            transacao.setCliente(cliente);
        }

        transacao.setValor(jpa.getValor());
        transacao.setTipo(jpa.getTipo());
        transacao.setData(jpa.getData());

        return transacao;
    }
}
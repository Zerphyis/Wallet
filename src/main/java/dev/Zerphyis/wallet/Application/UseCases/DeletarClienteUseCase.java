package dev.Zerphyis.wallet.Application.UseCases;

import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.UUID;

public class DeletarClienteUseCase {

    private final ClienteRepository repository;

    public DeletarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID informado."));

        // boolean possuiTransacoesAtivas = transacaoRepository.existsByClienteId(id);
        // if (possuiTransacoesAtivas) {
        //     throw new IllegalStateException("Não é possível excluir o cliente pois ele possui transações ativas vinculadas.");
        // }

        repository.deleteById(id);
    }
}

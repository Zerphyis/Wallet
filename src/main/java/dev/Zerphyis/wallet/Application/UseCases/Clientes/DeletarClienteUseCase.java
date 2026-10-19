package dev.Zerphyis.wallet.Application.UseCases.Clientes;

import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.UUID;

public class DeletarClienteUseCase {

    private final ClienteRepository repository;

    public DeletarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new ClienteNotFound("Cliente não encontrado com o ID informado."));

        repository.deleteById(id);
    }
}

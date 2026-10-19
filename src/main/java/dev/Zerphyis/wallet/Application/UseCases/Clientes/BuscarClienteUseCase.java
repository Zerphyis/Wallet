package dev.Zerphyis.wallet.Application.UseCases.Clientes;

import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Application.InterfaceCases.Clientes.BuscarClienteInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

public class BuscarClienteUseCase implements BuscarClienteInterfaceCase {

    private final ClienteRepository clienteRepository;

    public BuscarClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @Cacheable(value = "clientesCache", unless = "#result.isEmpty()")
    public Cliente execute(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFound("Cliente não encontrado com o ID informado."));
    }
}


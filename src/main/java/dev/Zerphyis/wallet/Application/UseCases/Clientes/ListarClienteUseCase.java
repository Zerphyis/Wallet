package dev.Zerphyis.wallet.Application.UseCases.Clientes;

import dev.Zerphyis.wallet.Application.InterfaceCases.Clientes.ListarClienteInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.List;

public class ListarClienteUseCase implements ListarClienteInterfaceCase {
    private final ClienteRepository clienteRepository;

    public ListarClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> execute() {
        return clienteRepository.findAll();
    }
}

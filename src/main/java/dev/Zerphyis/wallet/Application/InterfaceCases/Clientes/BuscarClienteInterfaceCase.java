package dev.Zerphyis.wallet.Application.InterfaceCases.Clientes;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

import java.util.UUID;

public interface BuscarClienteInterfaceCase {
    public Cliente execute(UUID id);
}

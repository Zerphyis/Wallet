package dev.Zerphyis.wallet.Application.InterfaceCases.Clientes;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

import java.util.UUID;

public interface AtualizarClienteInterfaceCase {
    public Cliente execute(UUID id, ClienteRequestDto requestDTO);
}

package dev.Zerphyis.wallet.Application.InterfaceCases.Clientes;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

public interface CadastraClienteInterfaceCase {
    public Cliente execute(ClienteRequestDto requestDTO);
}

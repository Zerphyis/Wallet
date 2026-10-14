package dev.Zerphyis.wallet.Application.UseCases;

import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Application.InterfaceCases.BuscarClienteInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.UUID;

public class BuscarClienteUseCase implements BuscarClienteInterfaceCase {

        private final ClienteRepository clienteRepository;

        public BuscarClienteUseCase(ClienteRepository clienteRepository) {
            this.clienteRepository = clienteRepository;
        }

        public Cliente execute(UUID id) {
            return clienteRepository.findById(id)
                    .orElseThrow(() -> new ClienteNotFound("Cliente não encontrado com o ID informado."));
        }
    }


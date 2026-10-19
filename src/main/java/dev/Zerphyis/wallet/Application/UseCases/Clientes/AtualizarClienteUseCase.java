package dev.Zerphyis.wallet.Application.UseCases.Clientes;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Application.Exceptions.DuplicatraException;
import dev.Zerphyis.wallet.Application.InterfaceCases.Clientes.AtualizarClienteInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.UUID;

public class AtualizarClienteUseCase implements AtualizarClienteInterfaceCase {
    private final ClienteRepository clienteRepository;

    public AtualizarClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(UUID id, ClienteRequestDto requestDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFound("Cliente não encontrado com o ID informado."));

        if (!clienteExistente.getCpf().equals(requestDTO.cpf())) {
            if (clienteRepository.existsByCpf(requestDTO.cpf())) {
                throw new DuplicatraException("Já existe outro cliente cadastrado com este CPF.");
            }
        }

        clienteExistente.setNome(requestDTO.nome());
        clienteExistente.setCpf(requestDTO.cpf());

        return clienteRepository.save(clienteExistente);
    }
}






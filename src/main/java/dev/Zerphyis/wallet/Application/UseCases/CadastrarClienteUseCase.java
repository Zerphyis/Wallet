package dev.Zerphyis.wallet.Application.UseCases;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Application.Exceptions.DuplicatraException;
import dev.Zerphyis.wallet.Application.InterfaceCases.CadastraClienteInterfaceCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;

import java.util.UUID;

public class CadastrarClienteUseCase implements CadastraClienteInterfaceCase {
    private final ClienteRepository repository;

    public CadastrarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente execute(ClienteRequestDto requestDto) {
        if (repository.existsByCpf(requestDto.nome())) {
            throw new DuplicatraException("Já existe um cliente cadastrado com este CPF.");

        }
        Cliente novoCliente = new Cliente(
                UUID.randomUUID(),
                requestDto.nome(),
                requestDto.cpf()
        );

        return repository.save(novoCliente);
    }
}






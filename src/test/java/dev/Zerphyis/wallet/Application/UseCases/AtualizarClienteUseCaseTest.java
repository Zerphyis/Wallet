package dev.Zerphyis.wallet.Application.UseCases;

import static org.junit.jupiter.api.Assertions.*;
import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class AtualizarClienteUseCaseTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AtualizarClienteUseCase useCase;

    @Test
    @DisplayName("Good Path: Deve atualizar o cliente com sucesso")
    void deveAtualizarClienteComSucesso() {
        UUID id = UUID.randomUUID();
        Cliente clienteExistente = new Cliente(id, "Otávio Antigo", "12345678900");
        ClienteRequestDto request = new ClienteRequestDto("Otávio Novo", "12345678900");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente atualizado = useCase.execute(id, request);

        assertEquals("Otávio Novo", atualizado.getNome());
        verify(clienteRepository).save(clienteExistente);
    }

}


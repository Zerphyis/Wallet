package dev.Zerphyis.wallet.Application.UseCases.Cliente;

import static org.junit.jupiter.api.Assertions.*;
import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Application.Exceptions.DuplicatraException;
import dev.Zerphyis.wallet.Application.UseCases.Clientes.AtualizarClienteUseCase;
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
        Cliente clienteExistente = new Cliente(id, "Marcus Antigo", "12345678900");
        ClienteRequestDto request = new ClienteRequestDto("Marcus Novo", "12345678900");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente atualizado = useCase.execute(id, request);

        assertEquals("Marcus Novo", atualizado.getNome());
        verify(clienteRepository).save(clienteExistente);
    }

    @Test
    @DisplayName("Bad Path: Deve lançar exceção se cliente não for encontrado para atualização")
    void deveLancarExcecaoClienteNaoEncontrado() {
        UUID id = UUID.randomUUID();
        ClienteRequestDto request = new ClienteRequestDto("Marcus", "12345678900");
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFound.class, () -> useCase.execute(id, request));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Bad Path: Deve lançar exceção ao alterar para um CPF já cadastrado por outro")
    void deveLancarExcecaoCpfDuplicadoNaAtualizacao() {
        UUID id = UUID.randomUUID();
        Cliente clienteExistente = new Cliente(id, "Marcus", "11111111111");
        ClienteRequestDto request = new ClienteRequestDto("Marcus", "22222222222");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.existsByCpf("22222222222")).thenReturn(true);

        assertThrows(DuplicatraException.class, () -> useCase.execute(id, request));
        verify(clienteRepository, never()).save(any());
    }
}


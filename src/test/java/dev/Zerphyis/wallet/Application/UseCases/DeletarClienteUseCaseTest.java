package dev.Zerphyis.wallet.Application.UseCases;

import static org.junit.jupiter.api.Assertions.*;

import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarClienteUseCaseTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private DeletarClienteUseCase useCase;

    @Test
    @DisplayName("Good Path: Deve deletar o cliente com sucesso")
    void deveDeletarClienteComSucesso() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente(id, "Otávio", "12345678900");

        when(repository.findById(id)).thenReturn(Optional.of(cliente));
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> useCase.execute(id));
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Bad Path: Deve lançar exceção ao tentar deletar cliente inexistente")
    void deveLancarExcecaoClienteNaoEncontradoAoDeletar() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFound.class, () -> useCase.execute(id));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(any());
    }
}
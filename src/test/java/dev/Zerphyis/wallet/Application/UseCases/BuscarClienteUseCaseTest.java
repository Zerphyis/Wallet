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
class BuscarClienteUseCaseTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private BuscarClienteUseCase useCase;

    @Test
    @DisplayName("Good Path: Deve retornar o cliente quando ID existir")
    void deveRetornarClientePorId() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente(id, "Otávio", "12345678900");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Bad Path: Deve lançar ClienteNotFound quando ID não existir")
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFound.class, () -> useCase.execute(id));
        verify(clienteRepository, times(1)).findById(id);
    }

}
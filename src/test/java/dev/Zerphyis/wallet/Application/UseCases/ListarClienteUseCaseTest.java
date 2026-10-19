package dev.Zerphyis.wallet.Application.UseCases;

import static org.junit.jupiter.api.Assertions.*;

import dev.Zerphyis.wallet.Application.UseCases.Clientes.ListarClienteUseCase;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ListarClienteUseCaseTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ListarClienteUseCase useCase;

    @Test
    @DisplayName("Good Path: Deve retornar lista de clientes")
    void deveRetornarListaDeClientes() {
        List<Cliente> clientes = List.of(
                new Cliente(UUID.randomUUID(), "Marcus", "12345678900"),
                new Cliente(UUID.randomUUID(), "Ana", "98765432100")
        );
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

}
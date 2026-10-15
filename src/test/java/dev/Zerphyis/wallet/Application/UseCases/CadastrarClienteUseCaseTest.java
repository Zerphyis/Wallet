package dev.Zerphyis.wallet.Application.UseCases;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Application.Exceptions.DuplicatraException;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CadastrarClienteUseCaseTest {
    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private CadastrarClienteUseCase useCase;

    @Test
    @DisplayName("Good Path: Deve cadastrar um cliente com sucesso")
    void deveCadastrarClienteComSucesso() {
        ClienteRequestDto request = new ClienteRequestDto("Marcus", "12345678900");
        when(repository.existsByCpf(request.cpf())).thenReturn(false);
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente clienteSalvo = useCase.execute(request);

        assertNotNull(clienteSalvo);
        assertEquals("Marcus", clienteSalvo.getNome());
        assertEquals("12345678900", clienteSalvo.getCpf());
        verify(repository, times(1)).existsByCpf(request.cpf());
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Bad Path: Deve lançar exceção ao tentar cadastrar CPF duplicado")
    void deveLancarExcecaoQuandoCpfJaExistir() {
        ClienteRequestDto request = new ClienteRequestDto("Marcus", "12345678900");
        when(repository.existsByCpf(request.cpf())).thenReturn(true);

        assertThrows(DuplicatraException.class, () -> useCase.execute(request));
        verify(repository, times(1)).existsByCpf(request.cpf());
        verify(repository, never()).save(any(Cliente.class));
    }

}
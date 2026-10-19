package dev.Zerphyis.wallet.Application.UseCases.Clientes;


import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

import java.util.List;
import java.util.UUID;

public class ClienteService {

    private final CadastrarClienteUseCase cadastrarClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final BuscarClienteUseCase buscarClientePorIdUseCase;
    private final ListarClienteUseCase listarClientesUseCase;
    private final DeletarClienteUseCase deletarClienteUseCase;

    public ClienteService(
            CadastrarClienteUseCase cadastrarClienteUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            BuscarClienteUseCase buscarClientePorIdUseCase,
            ListarClienteUseCase listarClientesUseCase,
            DeletarClienteUseCase deletarClienteUseCase) {
        this.cadastrarClienteUseCase = cadastrarClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
        this.listarClientesUseCase = listarClientesUseCase;
        this.deletarClienteUseCase = deletarClienteUseCase;
    }

    public Cliente cadastrar(ClienteRequestDto requestDTO) {
        return cadastrarClienteUseCase.execute(requestDTO);
    }

    public Cliente atualizar(UUID id, ClienteRequestDto requestDto) {
        return atualizarClienteUseCase.execute(id, requestDto);
    }

    public Cliente buscarPorId(UUID id) {
        return buscarClientePorIdUseCase.execute(id);
    }

    public List<Cliente> listar() {
        return listarClientesUseCase.execute();
    }

    public void deletar(UUID id) {
        deletarClienteUseCase.execute(id);
    }
}
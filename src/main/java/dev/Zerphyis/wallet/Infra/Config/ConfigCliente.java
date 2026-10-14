package dev.Zerphyis.wallet.Infra.Config;

import dev.Zerphyis.wallet.Application.UseCases.*;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCliente {
    @Bean
    public CadastrarClienteUseCase cadastrarClienteUseCase(ClienteRepository clienteRepository) {
        return new CadastrarClienteUseCase(clienteRepository);
    }

    @Bean
    public AtualizarClienteUseCase atualizarClienteUseCase(ClienteRepository clienteRepository) {
        return new AtualizarClienteUseCase(clienteRepository);
    }

    @Bean
    public BuscarClienteUseCase buscarClientePorIdUseCase(ClienteRepository clienteRepository) {
        return new BuscarClienteUseCase(clienteRepository);
    }


    @Bean
    public ListarClienteUseCase listarClientesUseCase(ClienteRepository clienteRepository) {
        return new ListarClienteUseCase(clienteRepository);
    }

    @Bean
    public DeletarClienteUseCase deletarClienteUseCase(ClienteRepository clienteRepository) {
        return new DeletarClienteUseCase(clienteRepository);
    }

    @Bean
    public ClienteService clienteService(
            CadastrarClienteUseCase cadastrarClienteUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            BuscarClienteUseCase buscarClientePorIdUseCase,
            ListarClienteUseCase listarClientesUseCase,
            DeletarClienteUseCase deletarClienteUseCase) {
        return new ClienteService(
                cadastrarClienteUseCase,
                atualizarClienteUseCase,
                buscarClientePorIdUseCase,
                listarClientesUseCase,
                deletarClienteUseCase
        );
    }
}

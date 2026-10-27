package dev.Zerphyis.wallet.Infra.Config;

import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.ConsultarExtratoESaldoInterfaceCase;
import dev.Zerphyis.wallet.Application.InterfaceCases.Transacao.RealizarTransacaoInterfaceCase;
import dev.Zerphyis.wallet.Application.UseCases.Transacao.ConsultarExtratoESaldoUseCase;
import dev.Zerphyis.wallet.Application.UseCases.Transacao.RealizarTransacaoUseCase;
import dev.Zerphyis.wallet.Application.UseCases.Transacao.ServiceTransacao;
import dev.Zerphyis.wallet.Domain.Repository.TransacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTransacao {

    @Bean
    public RealizarTransacaoUseCase realizarTransacaoUseCase(TransacaoRepository repository) {
        return new RealizarTransacaoUseCase(repository);
    }

    @Bean
    public ConsultarExtratoESaldoUseCase consultarExtratoESaldoUseCase(TransacaoRepository repository) {
        return new ConsultarExtratoESaldoUseCase(repository);
    }

    @Bean
    public ServiceTransacao serviceTransacao(
            RealizarTransacaoInterfaceCase realizarTransacaoUseCase,
            ConsultarExtratoESaldoInterfaceCase consultarExtratoESaldoUseCase
    ) {
        return new ServiceTransacao(realizarTransacaoUseCase, consultarExtratoESaldoUseCase);
    }
}

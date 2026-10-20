package dev.Zerphyis.wallet.Application.InterfaceCases.Transacao;

import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ConsultarExtratoESaldoInterfaceCase {
    public ExtratoResponse executar(UUID idCliente);
    public BigDecimal consultarSaldoApenas(UUID idCliente);
}

package dev.Zerphyis.wallet.Application.Dto.Transacao;

import dev.Zerphyis.wallet.Domain.Entitys.Transacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ExtratoResponse(UUID idCliente,
                              BigDecimal saldoConsolidado,
                              List<Transacao> transacoes){
}

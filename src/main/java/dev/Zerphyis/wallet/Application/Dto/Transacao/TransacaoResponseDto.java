package dev.Zerphyis.wallet.Application.Dto.Transacao;

import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoResponseDto(
        Long id,
        UUID idCliente,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime data
) {
    public static TransacaoResponseDto daEntidade(Transacao transacao) {
        return new TransacaoResponseDto(
                transacao.getId(),
                transacao.getCliente() != null ? transacao.getCliente().getId() : null,
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getData()
        );
    }
}
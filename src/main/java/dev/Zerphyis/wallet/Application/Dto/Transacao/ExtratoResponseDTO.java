package dev.Zerphyis.wallet.Application.Dto.Transacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ExtratoResponseDTO(
        UUID idCliente,
        BigDecimal saldoConsolidado,
        List<TransacaoResponseDto> transacoes
) {
    public static ExtratoResponseDTO doResponse(ExtratoResponse response) {
        List<TransacaoResponseDto> transacoesDTO = response.transacoes()
                .stream()
                .map(TransacaoResponseDto::daEntidade)
                .toList();

        return new ExtratoResponseDTO(
                response.idCliente(),
                response.saldoConsolidado(),
                transacoesDTO
        );
    }
}
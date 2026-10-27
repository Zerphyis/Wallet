package dev.Zerphyis.wallet.Application.Dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDto(
        int status,
        String mensagem,
        LocalDateTime timestamp,
        List<String> erros
) {
    public ErroResponseDto(int status, String mensagem) {
        this(status, mensagem, LocalDateTime.now(), null);
    }

    public ErroResponseDto(int status, String mensagem, List<String> erros) {
        this(status, mensagem, LocalDateTime.now(), erros);
    }
}
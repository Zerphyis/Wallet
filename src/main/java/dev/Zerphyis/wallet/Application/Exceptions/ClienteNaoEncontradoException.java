package dev.Zerphyis.wallet.Application.Exceptions;

import java.util.UUID;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(UUID idCliente) {
        super("Cliente não encontrado para o ID: " + idCliente);
    }

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}

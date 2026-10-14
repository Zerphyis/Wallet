package dev.Zerphyis.wallet.Application.Exceptions;

public class ClienteNotFound extends RuntimeException {
    public ClienteNotFound(String message) {
        super(message);
    }
}

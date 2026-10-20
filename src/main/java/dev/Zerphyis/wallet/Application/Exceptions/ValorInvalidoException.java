package dev.Zerphyis.wallet.Application.Exceptions;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }

    public ValorInvalidoException() {
        super("O valor da transação deve ser estritamente maior que zero.");
    }
}

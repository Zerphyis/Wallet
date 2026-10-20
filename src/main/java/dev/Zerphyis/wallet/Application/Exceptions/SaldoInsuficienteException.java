package dev.Zerphyis.wallet.Application.Exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException() {
        super("Transação negada: Saldo insuficiente para realizar o saque.");
    }
}

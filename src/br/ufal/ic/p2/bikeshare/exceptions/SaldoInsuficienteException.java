package br.ufal.ic.p2.bikeshare.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}

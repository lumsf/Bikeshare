package br.ufal.ic.p2.bikeshare.exceptions;

public class ValidadeCartaoInvalidaException extends RuntimeException {
    public ValidadeCartaoInvalidaException() {
        super("Validade do cartao invalida.");
    }
}

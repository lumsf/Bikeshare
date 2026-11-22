package br.ufal.ic.p2.bikeshare.exceptions;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException() {
        super("Data invalida.");
    }
}

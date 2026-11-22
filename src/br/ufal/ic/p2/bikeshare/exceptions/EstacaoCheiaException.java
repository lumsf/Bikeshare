package br.ufal.ic.p2.bikeshare.exceptions;

public class EstacaoCheiaException extends RuntimeException {
    public EstacaoCheiaException() {
        super("Estacao cheia.");
    }
}

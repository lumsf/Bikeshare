package br.ufal.ic.p2.bikeshare.exceptions;

public class PlanoInvalidoException extends RuntimeException {
    public PlanoInvalidoException() {
        super("Plano invalido.");
    }
}

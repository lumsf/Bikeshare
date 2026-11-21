package br.ufal.ic.p2.bikeshare.exceptions;

public class TipoInvalidoException extends RuntimeException {
    public TipoInvalidoException() {
        super("Tipo invalido.");
    }
}

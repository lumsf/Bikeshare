package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorNuloException extends RuntimeException {
    public ValorNuloException() {
        super("Valor nao pode ser nulo.");
    }
}

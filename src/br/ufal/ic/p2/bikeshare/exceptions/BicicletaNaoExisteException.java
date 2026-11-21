package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoExisteException extends RuntimeException {
    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}

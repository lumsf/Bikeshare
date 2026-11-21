package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaJaExisteException extends RuntimeException {
    public BicicletaJaExisteException() {
        super("Bicicleta ja existe.");
    }
}

package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaIdNulaException extends RuntimeException {
    public BicicletaIdNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}

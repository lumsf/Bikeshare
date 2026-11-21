package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeNulaException extends RuntimeException {
    public CapacidadeNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}

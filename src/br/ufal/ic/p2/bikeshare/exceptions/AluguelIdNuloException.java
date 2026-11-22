package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelIdNuloException extends RuntimeException {
    public AluguelIdNuloException() {
        super("Identificacao do aluguel nao pode ser nula.");
    }
}

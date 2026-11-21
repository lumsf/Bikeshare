package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeDeveSerPositivaException extends RuntimeException {
    public CapacidadeDeveSerPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}

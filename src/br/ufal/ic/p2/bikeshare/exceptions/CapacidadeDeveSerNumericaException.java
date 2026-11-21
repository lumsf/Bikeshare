package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeDeveSerNumericaException extends RuntimeException {
    public CapacidadeDeveSerNumericaException() {
        super("Capacidade deve ser numerica.");
    }
}

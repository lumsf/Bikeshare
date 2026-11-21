package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaNuloException extends RuntimeException {
    public NivelBateriaNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}

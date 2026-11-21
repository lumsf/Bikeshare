package br.ufal.ic.p2.bikeshare.exceptions;

public class EstacaoIdNulaException extends RuntimeException {
    public EstacaoIdNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}

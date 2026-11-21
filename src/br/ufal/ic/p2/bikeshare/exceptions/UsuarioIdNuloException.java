package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioIdNuloException extends RuntimeException {
    public UsuarioIdNuloException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}

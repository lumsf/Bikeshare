package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoExisteException extends RuntimeException {
    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}

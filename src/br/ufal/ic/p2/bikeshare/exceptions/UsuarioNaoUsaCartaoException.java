package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoUsaCartaoException extends RuntimeException {
    public UsuarioNaoUsaCartaoException() {
        super("Usuario nao usa cartao.");
    }
}

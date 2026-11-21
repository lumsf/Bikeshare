package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoUsaPixException extends RuntimeException {
    public UsuarioNaoUsaPixException() {
        super("Usuario nao usa pix.");
    }
}

package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioInadimplenteException extends RuntimeException {
    public UsuarioInadimplenteException() {
        super("Usuario inadimplente.");
    }
}

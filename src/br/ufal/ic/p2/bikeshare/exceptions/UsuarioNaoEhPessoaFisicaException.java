package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoEhPessoaFisicaException extends RuntimeException {
    public UsuarioNaoEhPessoaFisicaException() {
        super("Usuario nao eh pessoa fisica.");
    }
}

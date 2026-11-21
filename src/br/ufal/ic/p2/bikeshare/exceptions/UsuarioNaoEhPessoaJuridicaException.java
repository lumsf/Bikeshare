package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoEhPessoaJuridicaException extends RuntimeException {
    public UsuarioNaoEhPessoaJuridicaException() {
        super("Usuario nao eh pessoa juridica.");
    }
}

package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioJaPossuiAluguelEmAndamentoException extends RuntimeException {
    public UsuarioJaPossuiAluguelEmAndamentoException() {
        super("Usuario ja possui um aluguel em andamento.");
    }
}

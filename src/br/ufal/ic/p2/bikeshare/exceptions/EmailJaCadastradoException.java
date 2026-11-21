package br.ufal.ic.p2.bikeshare.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException() {
        super("Email ja cadastrado.");
    }
}

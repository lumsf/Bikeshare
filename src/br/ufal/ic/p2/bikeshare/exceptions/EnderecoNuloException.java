package br.ufal.ic.p2.bikeshare.exceptions;

public class EnderecoNuloException extends RuntimeException {
    public EnderecoNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}

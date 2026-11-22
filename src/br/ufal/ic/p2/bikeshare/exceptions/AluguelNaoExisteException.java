package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelNaoExisteException extends RuntimeException {
    public AluguelNaoExisteException() {
        super("Aluguel nao existe.");
    }
}

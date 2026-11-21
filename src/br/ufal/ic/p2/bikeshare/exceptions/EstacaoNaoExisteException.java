package br.ufal.ic.p2.bikeshare.exceptions;

public class EstacaoNaoExisteException extends RuntimeException {
    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}

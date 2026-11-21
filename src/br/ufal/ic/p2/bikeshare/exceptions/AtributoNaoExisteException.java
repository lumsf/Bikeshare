package br.ufal.ic.p2.bikeshare.exceptions;

public class AtributoNaoExisteException extends RuntimeException {
    public AtributoNaoExisteException() {
        super("Atributo nao existe.");
    }
}

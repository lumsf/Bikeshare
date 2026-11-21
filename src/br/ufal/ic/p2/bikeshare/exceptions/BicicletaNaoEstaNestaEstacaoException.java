package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoEstaNestaEstacaoException extends RuntimeException {
    public BicicletaNaoEstaNestaEstacaoException() {
        super("Bicicleta nao esta nesta estacao.");
    }
}

package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelNaoEstaEmAndamentoException extends RuntimeException {
    public AluguelNaoEstaEmAndamentoException() {
        super("Aluguel nao esta em andamento.");
    }
}

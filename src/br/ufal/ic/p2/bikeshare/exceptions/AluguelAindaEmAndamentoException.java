package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelAindaEmAndamentoException extends RuntimeException {
    public AluguelAindaEmAndamentoException() {
        super("Aluguel ainda esta em andamento.");
    }
}

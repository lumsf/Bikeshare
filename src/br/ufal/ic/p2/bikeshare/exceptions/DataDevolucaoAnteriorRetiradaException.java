package br.ufal.ic.p2.bikeshare.exceptions;

public class DataDevolucaoAnteriorRetiradaException extends RuntimeException {
    public DataDevolucaoAnteriorRetiradaException() {
        super("Data de devolucao anterior a data de retirada.");
    }
}

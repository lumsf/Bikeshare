package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDeveSerPositivoException extends RuntimeException {
    public ValorDeveSerPositivoException() {
        super("Valor deve ser positivo.");
    }
}

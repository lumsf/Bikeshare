package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDeveSerNumericoException extends RuntimeException {
    public ValorDeveSerNumericoException() {
        super("Valor deve ser numerico.");
    }
}

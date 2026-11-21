package br.ufal.ic.p2.bikeshare.exceptions;

public class MensalidadeDeveSerNumericaException extends RuntimeException {
    public MensalidadeDeveSerNumericaException() {
        super("Mensalidade deve ser numerica.");
    }
}

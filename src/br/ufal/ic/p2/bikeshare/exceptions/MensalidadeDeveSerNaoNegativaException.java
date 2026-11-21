package br.ufal.ic.p2.bikeshare.exceptions;

public class MensalidadeDeveSerNaoNegativaException extends RuntimeException {
    public MensalidadeDeveSerNaoNegativaException() {
        super("Mensalidade deve ser nao-negativa.");
    }
}

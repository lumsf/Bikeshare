package br.ufal.ic.p2.bikeshare.exceptions;

public class MensalidadeNulaNaoPermitidaException extends RuntimeException {
    public MensalidadeNulaNaoPermitidaException() {
        super("Mensalidade nao pode ser nula.");
    }
}

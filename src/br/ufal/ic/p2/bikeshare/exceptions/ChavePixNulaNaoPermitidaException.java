package br.ufal.ic.p2.bikeshare.exceptions;

public class ChavePixNulaNaoPermitidaException extends RuntimeException {
    public ChavePixNulaNaoPermitidaException() {
        super("Chave pix nao pode ser nula.");
    }
}

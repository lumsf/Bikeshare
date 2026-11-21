package br.ufal.ic.p2.bikeshare.exceptions;

public class PlanoNaoPossuiMensalidadeException extends RuntimeException {
    public PlanoNaoPossuiMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}

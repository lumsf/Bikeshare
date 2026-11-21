package br.ufal.ic.p2.bikeshare.exceptions;

public class MetodoPagamentoInvalidoException extends RuntimeException {
    public MetodoPagamentoInvalidoException() {
        super("Valor de metodo de pagamento invalido.");
    }
}

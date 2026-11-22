package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioComAluguelEmAndamentoException extends RuntimeException {
    public UsuarioComAluguelEmAndamentoException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}

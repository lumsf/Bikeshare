package br.ufal.ic.p2.bikeshare.pagamento;

import java.io.Serializable;

/**
 * Contrato abstrato para diferentes estratégias de pagamento.
 */
public abstract class MetodoPagamento implements Serializable {

    public abstract String getTipo();

    public String getNumeroCartao() {
        throw new br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaCartaoException();
    }

    public String getValidadeCartao() {
        throw new br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaCartaoException();
    }

    public String getChavePix() {
        throw new br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaPixException();
    }

    public double getSaldo() {
        throw new br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaSaldoException();
    }

    public void adicionarSaldo(double valor) {
        throw new br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaSaldoException();
    }
}

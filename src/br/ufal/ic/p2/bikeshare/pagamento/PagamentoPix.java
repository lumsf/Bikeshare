package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exceptions.ChavePixNulaNaoPermitidaException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class PagamentoPix extends MetodoPagamento {

    private String chavePix;

    public PagamentoPix(String chavePix) {
        setChavePix(chavePix);
    }

    private void setChavePix(String chave) {
        if (Formatador.estaVazio(chave)) {
            throw new ChavePixNulaNaoPermitidaException();
        }
        this.chavePix = chave;
    }

    @Override
    public String getTipo() {
        return "pix";
    }

    @Override
    public String getChavePix() {
        return chavePix;
    }
}

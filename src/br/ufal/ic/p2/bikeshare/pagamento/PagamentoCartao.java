package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exceptions.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.ValidadeCartaoInvalidaException;
import br.ufal.ic.p2.bikeshare.exceptions.ValidadeCartaoNulaException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Implementação de pagamento via cartão de crédito ou débito.
 */
public class PagamentoCartao extends MetodoPagamento {

    private String numeroCartao;
    private String validade;

    public PagamentoCartao(String numeroCartao, String validade) {
        setNumeroCartao(numeroCartao);
        setValidade(validade);
    }

    private void setNumeroCartao(String numero) {
        if (Formatador.estaVazio(numero)) {
            throw new NumeroCartaoNuloException();
        }
        this.numeroCartao = numero;
    }

    private void setValidade(String validade) {
        if (Formatador.estaVazio(validade)) {
            throw new ValidadeCartaoNulaException();
        }
        if (!validarFormato(validade)) {
            throw new ValidadeCartaoInvalidaException();
        }
        this.validade = validade;
    }

    private boolean validarFormato(String validade) {
        if (validade == null) return false;
        String[] partes = validade.split("/");
        if (partes.length != 2) return false;
        String mesParte = partes[0];
        String anoParte = partes[1];
        if (mesParte.length() != 2 || anoParte.length() != 4) return false;
        for (char c : mesParte.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }
        for (char c : anoParte.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }
        int mes = Integer.parseInt(mesParte);
        return mes >= 1 && mes <= 12;
    }

    @Override
    public String getTipo() {
        return "cartao";
    }

    @Override
    public String getNumeroCartao() {
        return numeroCartao;
    }

    @Override
    public String getValidadeCartao() {
        return validade;
    }
}

package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Implementação de pagamento via saldo pré-carregado em conta.
 */
public class PagamentoSaldoPreCarregado extends MetodoPagamento {

    private double saldo;

    public PagamentoSaldoPreCarregado() {
        this.saldo = 0.0;
    }

    public PagamentoSaldoPreCarregado(double saldoAnterior) {
        this.saldo = saldoAnterior;
    }

    @Override
    public String getTipo() {
        return "saldoPreCarregado";
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public String getSaldoFormatado() {
        return Formatador.formatarDinheiro(saldo);
    }
}

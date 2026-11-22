package br.ufal.ic.p2.bikeshare.usuario;

import java.io.Serializable;

import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoEhPessoaFisicaException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoEhPessoaJuridicaException;
import br.ufal.ic.p2.bikeshare.pagamento.MetodoPagamento;
import br.ufal.ic.p2.bikeshare.pagamento.PagamentoSaldoPreCarregado;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Classe base abstrata para usuários do sistema.
 */
public abstract class Usuario implements Serializable {

    private final String id;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String plano;
    private double mensalidade;
    private MetodoPagamento metodoPagamento;
    private double saldoPreCarregadoHistorico;
    private String aluguelAtivoId;
    private double totalPendencias;

    public Usuario(String id, String nome, String email, String senha) {
        this.id = id;
        this.estado = "ativo";
        this.plano = "avulso";
        this.mensalidade = 0.0;
        this.metodoPagamento = null;
        this.saldoPreCarregadoHistorico = 0.0;
        this.aluguelAtivoId = null;
        this.totalPendencias = 0.0;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    public String getId() { return id; }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public String getSenha() { return senha; }

    public String getEstado() { return estado; }

    public String getPlano() { return plano; }

    public double getMensalidade() { return mensalidade; }

    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }

    public boolean temMetodoPagamento() { return metodoPagamento != null; }

    public double getSaldoPreCarregadoHistorico() { return saldoPreCarregadoHistorico; }

    public void setSaldoPreCarregadoHistorico(double saldo) {
        this.saldoPreCarregadoHistorico = saldo;
    }

    public String getAluguelAtivoId() {
        return aluguelAtivoId != null ? aluguelAtivoId : "nenhum";
    }

    public boolean temAluguelAtivo() {
        return aluguelAtivoId != null;
    }

    public void setAluguelAtivoId(String aluguelAtivoId) {
        this.aluguelAtivoId = aluguelAtivoId;
    }

    public double getTotalPendencias() {
        return totalPendencias;
    }

    public void adicionarPendencia(double valor) {
        this.totalPendencias += valor;
    }

    public void setNome(String nome) {
        if (Formatador.estaVazio(nome)) {
            throw new IllegalArgumentException("Nome nao pode ser nulo.");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (Formatador.estaVazio(email)) {
            throw new IllegalArgumentException("Email nao pode ser nulo.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email invalido.");
        }
        this.email = email;
    }

    public void setSenha(String senha) {
        if (Formatador.estaVazio(senha)) {
            throw new IllegalArgumentException("Senha nao pode ser nula.");
        }
        this.senha = senha;
    }

    public void setPlano(String plano, double mensalidade) {
        this.plano = plano;
        this.mensalidade = mensalidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMetodoPagamento(MetodoPagamento metodo) {
        if (this.metodoPagamento instanceof PagamentoSaldoPreCarregado) {
            this.saldoPreCarregadoHistorico = this.metodoPagamento.getSaldo();
        }
        this.metodoPagamento = metodo;
    }

    public boolean temEmail(String outroEmail) {
        return this.email.equalsIgnoreCase(outroEmail.trim());
    }

    public abstract String getTipo();

    public String getCpf() {
        throw new UsuarioNaoEhPessoaFisicaException();
    }

    public String getCnpj() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getRazaoSocial() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }
}

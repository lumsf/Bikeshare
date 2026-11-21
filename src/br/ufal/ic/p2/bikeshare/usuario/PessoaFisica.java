package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.util.Formatador;

public class PessoaFisica extends Usuario {

    private final String cpf;

    public PessoaFisica(String id, String nome, String email, String senha, String cpf) {
        super(id, nome, email, senha);
        if (Formatador.estaVazio(cpf)) {
            throw new IllegalArgumentException("CPF nao pode ser nulo.");
        }
        this.cpf = cpf;
    }

    @Override
    public String getTipo() { return "pessoaFisica"; }

    @Override
    public String getCpf() { return cpf; }
}

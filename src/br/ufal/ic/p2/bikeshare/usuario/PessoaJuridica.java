package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Representa um usuário do tipo pessoa jurídica (CNPJ e razão social).
 */
public class PessoaJuridica extends Usuario {

    private final String cnpj;
    private final String razaoSocial;

    public PessoaJuridica(String id, String nome, String email, String senha,
                          String cnpj, String razaoSocial) {
        super(id, nome, email, senha);
        if (Formatador.estaVazio(cnpj)) {
            throw new IllegalArgumentException("CNPJ nao pode ser nulo.");
        }
        if (Formatador.estaVazio(razaoSocial)) {
            throw new IllegalArgumentException("Razao social nao pode ser nula.");
        }
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getTipo() { return "pessoaJuridica"; }

    @Override
    public String getCnpj() { return cnpj; }

    @Override
    public String getRazaoSocial() { return razaoSocial; }
}

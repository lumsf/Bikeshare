package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeDeveSerNaoNegativaException;
import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeDeveSerNumericaException;
import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeNulaNaoPermitidaException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoNaoPossuiMensalidadeException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class UsuarioFabrica {

    private static final String PLANO_AVULSO = "avulso";
    private static final String PLANO_ASSINANTE = "assinante";
    private static final double MENSALIDADE_PADRAO_ASSINANTE = 30.0;

    public Usuario criarPessoaFisica(String id, String nome, String email, String senha, String cpf) {
        return new PessoaFisica(id, nome, email, senha, cpf);
    }

    public Usuario criarPessoaJuridica(String id, String nome, String email, String senha,
                                        String cnpj, String razaoSocial) {
        return new PessoaJuridica(id, nome, email, senha, cnpj, razaoSocial);
    }

    public Usuario criarPessoaFisicaComPlano(String id, String nome, String email, String senha,
                                              String cpf, String plano, String mensalidadeStr) {
        double mensalidade = validarEConverterPlano(plano, mensalidadeStr);
        PessoaFisica usuario = new PessoaFisica(id, nome, email, senha, cpf);
        if (!plano.equals(PLANO_AVULSO)) {
            usuario.setPlano(plano, mensalidade);
        }
        return usuario;
    }

    public Usuario criarPessoaJuridicaComPlano(String id, String nome, String email, String senha,
                                                String cnpj, String razaoSocial,
                                                String plano, String mensalidadeStr) {
        double mensalidade = validarEConverterPlano(plano, mensalidadeStr);
        PessoaJuridica usuario = new PessoaJuridica(id, nome, email, senha, cnpj, razaoSocial);
        if (!plano.equals(PLANO_AVULSO)) {
            usuario.setPlano(plano, mensalidade);
        }
        return usuario;
    }

    private double validarEConverterPlano(String plano, String mensalidadeStr) {
        if (!plano.equals(PLANO_AVULSO) && !plano.equals(PLANO_ASSINANTE)) {
            throw new PlanoInvalidoException();
        }
        if (plano.equals(PLANO_AVULSO)) {
            if (!Formatador.estaVazio(mensalidadeStr) && Formatador.ehNumero(mensalidadeStr)) {
                double val = Formatador.lerDinheiro(mensalidadeStr);
                if (val != 0) {
                    throw new PlanoNaoPossuiMensalidadeException();
                }
            }
            return 0.0;
        }
        if (Formatador.estaVazio(mensalidadeStr)) {
            throw new MensalidadeNulaNaoPermitidaException();
        }
        if (!Formatador.ehNumero(mensalidadeStr)) {
            throw new MensalidadeDeveSerNumericaException();
        }
        double val = Formatador.lerDinheiro(mensalidadeStr);
        if (val < 0) {
            throw new MensalidadeDeveSerNaoNegativaException();
        }
        return val;
    }

    public double getMensalidadePadrao() {
        return MENSALIDADE_PADRAO_ASSINANTE;
    }
}

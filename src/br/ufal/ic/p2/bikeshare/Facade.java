package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.exceptions.MetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioFabrica;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioRepositorio;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioServico;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class Facade {

    private static final String AINDA_NAO_IMPLEMENTADO = "Ainda não implementado.";

    private UsuarioRepositorio usuarioRepositorio;
    private UsuarioServico usuarioServico;

    public Facade() {
        inicializar();
    }

    private void inicializar() {
        this.usuarioRepositorio = UsuarioRepositorio.carregar();
        UsuarioFabrica fabrica = new UsuarioFabrica();
        this.usuarioServico = new UsuarioServico(usuarioRepositorio, fabrica);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj) {
        if ("pessoaFisica".equals(tipo)) {
            return usuarioServico.criarUsuarioPessoaFisica(nome, email, senha, tipo, cpfOuCnpj);
        } else if ("pessoaJuridica".equals(tipo)) {
            throw new TipoInvalidoException();
        } else {
            throw new TipoInvalidoException();
        }
    }

    public String criarUsuario(String nome, String email, String senha, String tipo,
                                String cnpj, String razaoSocial) {
        if ("pessoaFisica".equals(tipo)) {
            throw new TipoInvalidoException();
        } else if ("pessoaJuridica".equals(tipo)) {
            return usuarioServico.criarUsuarioPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial);
        } else {
            throw new TipoInvalidoException();
        }
    }

    public String criarUsuario(String nome, String email, String senha, String tipo,
                                String cpf, String plano, String mensalidade) {
        if ("pessoaFisica".equals(tipo)) {
            return usuarioServico.criarUsuarioPessoaFisicaComPlano(nome, email, senha, tipo, cpf, plano, mensalidade);
        } else if ("pessoaJuridica".equals(tipo)) {
            throw new TipoInvalidoException();
        } else {
            throw new TipoInvalidoException();
        }
    }

    public String criarUsuario(String nome, String email, String senha, String tipo,
                                String cnpj, String razaoSocial, String plano, String mensalidade) {
        if ("pessoaFisica".equals(tipo)) {
            throw new TipoInvalidoException();
        } else if ("pessoaJuridica".equals(tipo)) {
            return usuarioServico.criarUsuarioPessoaJuridicaComPlano(
                    nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
        } else {
            throw new TipoInvalidoException();
        }
    }

    public String getAtributoUsuario(String usuario, String atributo) {
        return usuarioServico.getAtributoUsuario(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) {
        return usuarioServico.getUsuarioPorEmail(email);
    }

    public void alterarUsuario(String usuario, String atributo, String valor) {
        usuarioServico.alterarAtributoSimples(usuario, atributo, valor);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String chave) {
        if (!"metodoPagamento".equals(atributo)) {
            throw new br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoPodeSerAlteradoException();
        }
        if (!"pix".equals(valor1)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuarioServico.alterarMetodoPagamentoPix(usuario, atributo, valor1, chave);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1,
                                String numero, String validade) {
        if (!"metodoPagamento".equals(atributo)) {
            throw new br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoPodeSerAlteradoException();
        }
        if (!"cartao".equals(valor1)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuarioServico.alterarMetodoPagamentoCartao(usuario, atributo, valor1, numero, validade);
    }

    public void removerUsuario(String usuario) {
        usuarioServico.removerUsuario(usuario);
    }

    public String criarEstacao(String nome, String endereco, String capacidade) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void removerBicicleta(String estacao, String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getBicicletasDisponiveis(String estacao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getVagasDisponiveis(String estacao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getEstadoBicicleta(String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void devolverBicicleta(String aluguel, String estacao, String data) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getAtributoAluguel(String aluguel, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void lancaPenalidade(String usuario, String tipo, String valor, String motivo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void concluiManutencao(String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void lancaDefeito(String bicicleta, String descricao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getTotalFaturado(String dataInicial, String dataFinal) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void undo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void redo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void zerarSistema() {
        usuarioRepositorio.zerar();
        inicializar();
    }

    public void encerrarSistema() {
        usuarioRepositorio.salvar();
    }
}

package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.aluguel.AluguelRepositorio;
import br.ufal.ic.p2.bikeshare.aluguel.AluguelServico;
import br.ufal.ic.p2.bikeshare.bicicleta.BicicletaRepositorio;
import br.ufal.ic.p2.bikeshare.estacao.EstacaoRepositorio;
import br.ufal.ic.p2.bikeshare.estacao.EstacaoServico;
import br.ufal.ic.p2.bikeshare.exceptions.MetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.TipoInvalidoException;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioFabrica;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioRepositorio;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioServico;

/**
 * Fachada principal do sistema Bikeshare.
 * Centraliza as operações do sistema delegando para os serviços especialistas.
 */
public class Facade {

    private static final String AINDA_NAO_IMPLEMENTADO = "Ainda não implementado.";

    private UsuarioRepositorio usuarioRepositorio;
    private UsuarioServico usuarioServico;
    private EstacaoRepositorio estacaoRepositorio;
    private BicicletaRepositorio bicicletaRepositorio;
    private EstacaoServico estacaoServico;
    private AluguelRepositorio aluguelRepositorio;
    private AluguelServico aluguelServico;

    public Facade() {
        inicializar();
    }

    private void inicializar() {
        this.usuarioRepositorio = UsuarioRepositorio.carregar();
        UsuarioFabrica fabrica = new UsuarioFabrica();
        this.usuarioServico = new UsuarioServico(usuarioRepositorio, fabrica);
        this.estacaoRepositorio = EstacaoRepositorio.carregar();
        this.bicicletaRepositorio = BicicletaRepositorio.carregar();
        this.estacaoServico = new EstacaoServico(estacaoRepositorio, bicicletaRepositorio);
        this.aluguelRepositorio = AluguelRepositorio.carregar();
        this.aluguelServico = new AluguelServico(aluguelRepositorio, usuarioRepositorio, estacaoRepositorio, bicicletaRepositorio);
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
        return estacaoServico.criarEstacao(nome, endereco, capacidade);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) {
        estacaoServico.adicionarBicicleta(estacao, bicicleta, tipo);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) {
        estacaoServico.adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) {
        estacaoServico.removerBicicleta(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) {
        return estacaoServico.getAtributoBicicleta(bicicleta, atributo);
    }

    public String getBicicletasDisponiveis(String estacao) {
        return String.valueOf(estacaoServico.getBicicletasDisponiveis(estacao));
    }

    public String getVagasDisponiveis(String estacao) {
        return String.valueOf(estacaoServico.getVagasDisponiveis(estacao));
    }

    public String getEstadoBicicleta(String bicicleta) {
        return estacaoServico.getEstadoBicicleta(bicicleta);
    }

    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) {
        return aluguelServico.retirarBicicleta(usuario, estacao, bicicleta, data);
    }

    public void devolverBicicleta(String aluguel, String estacao, String data) {
        aluguelServico.devolverBicicleta(aluguel, estacao, data);
    }

    public String getHistoricoAlugueis(String usuario) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getAtributoAluguel(String aluguel, String atributo) {
        return aluguelServico.getAtributoAluguel(aluguel, atributo);
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
        estacaoRepositorio.zerar();
        bicicletaRepositorio.zerar();
        aluguelRepositorio.zerar();
        inicializar();
    }

    public void encerrarSistema() {
        usuarioRepositorio.salvar();
        estacaoRepositorio.salvar();
        bicicletaRepositorio.salvar();
        aluguelRepositorio.salvar();
    }
}

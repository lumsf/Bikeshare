package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.pagamento.*;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Serviço que implementa as regras de negócio relacionadas aos usuários.
 */
public class UsuarioServico {

    private static final String PLANO_AVULSO = "avulso";
    private static final String PLANO_ASSINANTE = "assinante";

    private final UsuarioRepositorio repositorio;
    private final UsuarioFabrica fabrica;

    public UsuarioServico(UsuarioRepositorio repositorio, UsuarioFabrica fabrica) {
        this.repositorio = repositorio;
        this.fabrica = fabrica;
    }

    public String criarUsuarioPessoaFisica(String nome, String email, String senha,
                                            String tipo, String cpf) {
        validarTipoPessoaFisicaSemCnpj(tipo);
        validarEmailDisponivel(email);
        String id = repositorio.gerarProximoId();
        Usuario usuario = fabrica.criarPessoaFisica(id, nome, email, senha, cpf);
        repositorio.adicionar(usuario);
        return id;
    }

    public String criarUsuarioPessoaJuridica(String nome, String email, String senha,
                                              String tipo, String cnpj, String razaoSocial) {
        validarTipoPessoaJuridicaSemCpf(tipo);
        validarEmailDisponivel(email);
        String id = repositorio.gerarProximoId();
        Usuario usuario = fabrica.criarPessoaJuridica(id, nome, email, senha, cnpj, razaoSocial);
        repositorio.adicionar(usuario);
        return id;
    }

    public String criarUsuarioPessoaFisicaComPlano(String nome, String email, String senha,
                                                    String tipo, String cpf,
                                                    String plano, String mensalidade) {
        validarTipoPessoaFisicaSemCnpj(tipo);
        validarEmailDisponivel(email);
        String id = repositorio.gerarProximoId();
        Usuario usuario = fabrica.criarPessoaFisicaComPlano(id, nome, email, senha, cpf, plano, mensalidade);
        repositorio.adicionar(usuario);
        return id;
    }

    public String criarUsuarioPessoaJuridicaComPlano(String nome, String email, String senha,
                                                      String tipo, String cnpj, String razaoSocial,
                                                      String plano, String mensalidade) {
        validarTipoPessoaJuridicaSemCpf(tipo);
        validarEmailDisponivel(email);
        String id = repositorio.gerarProximoId();
        Usuario usuario = fabrica.criarPessoaJuridicaComPlano(id, nome, email, senha, cnpj, razaoSocial, plano, mensalidade);
        repositorio.adicionar(usuario);
        return id;
    }

    public String getAtributoUsuario(String usuarioId, String atributo) {
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        return resolverAtributo(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) {
        return repositorio.buscarPorEmail(email).getId();
    }

    private String resolverAtributo(Usuario u, String atributo) {
        if (Formatador.estaVazio(atributo)) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":           return u.getNome();
            case "email":          return u.getEmail();
            case "senha":          return u.getSenha();
            case "tipo":           return u.getTipo();
            case "estado":         return u.getEstado();
            case "plano":          return u.getPlano();
            case "cpf":            return u.getCpf();
            case "cnpj":           return u.getCnpj();
            case "razaoSocial":    return u.getRazaoSocial();
            case "mensalidade":
                if (u.getPlano().equals(PLANO_AVULSO)) {
                    throw new PlanoNaoPossuiMensalidadeException();
                }
                return Formatador.formatarDinheiro(u.getMensalidade());
            case "metodoPagamento":
                if (!u.temMetodoPagamento()) return "nenhum";
                return u.getMetodoPagamento().getTipo();
            case "numeroCartao":
                if (!u.temMetodoPagamento()) {
                    throw new UsuarioNaoUsaCartaoException();
                }
                return u.getMetodoPagamento().getNumeroCartao();
            case "validadeCartao":
                if (!u.temMetodoPagamento()) {
                    throw new UsuarioNaoUsaCartaoException();
                }
                return u.getMetodoPagamento().getValidadeCartao();
            case "chavePix":
                if (!u.temMetodoPagamento()) {
                    throw new UsuarioNaoUsaPixException();
                }
                return u.getMetodoPagamento().getChavePix();
            case "saldo":
                if (!u.temMetodoPagamento()) {
                    throw new UsuarioNaoUsaSaldoException();
                }
                return Formatador.formatarDinheiro(u.getMetodoPagamento().getSaldo());
            case "aluguelAtivo":
                return u.getAluguelAtivoId();
            case "totalPendencias":
                return Formatador.formatarDinheiro(u.getTotalPendencias());
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void alterarAtributoSimples(String usuarioId, String atributo, String valor) {
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        if ("metodoPagamento".equals(atributo)) {
            alterarMetodoPagamentoGenerico(usuarioId, atributo, valor);
            return;
        }
        switch (atributo) {
            case "nome":
                usuario.setNome(valor);
                break;
            case "email":
                if (!usuario.temEmail(valor)) {
                    if (repositorio.emailJaCadastradoExceto(valor, usuarioId)) {
                        throw new EmailJaCadastradoException();
                    }
                }
                usuario.setEmail(valor);
                break;
            case "senha":
                usuario.setSenha(valor);
                break;
            case "plano":
                alterarPlano(usuario, valor);
                break;
            case "saldo":
                alterarSaldo(usuario, valor);
                break;
            case "tipo":
            case "estado":
            case "totalPendencias":
            case "aluguelAtivo":
                throw new AtributoNaoPodeSerAlteradoException();
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    public void alterarMetodoPagamentoPix(String usuarioId, String atributo, String tipoPagamento, String chave) {
        validarAtributoEhMetodoPagamento(atributo);
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        if (!"pix".equals(tipoPagamento)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuario.setMetodoPagamento(new PagamentoPix(chave));
    }

    public void alterarMetodoPagamentoCartao(String usuarioId, String atributo,
                                              String tipoPagamento, String numero, String validade) {
        validarAtributoEhMetodoPagamento(atributo);
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        if (!"cartao".equals(tipoPagamento)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuario.setMetodoPagamento(new PagamentoCartao(numero, validade));
    }

    public void alterarMetodoPagamentoGenerico(String usuarioId, String atributo, String tipoPagamento) {
        validarAtributoEhMetodoPagamento(atributo);
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        if (!"saldoPreCarregado".equals(tipoPagamento)) {
            throw new MetodoPagamentoInvalidoException();
        }
        double saldoAnterior = usuario.getSaldoPreCarregadoHistorico();
        if (usuario.getMetodoPagamento() instanceof PagamentoSaldoPreCarregado) {
            saldoAnterior = usuario.getMetodoPagamento().getSaldo();
        }
        usuario.setMetodoPagamento(new PagamentoSaldoPreCarregado(saldoAnterior));
    }

    public void removerUsuario(String usuarioId) {
        Usuario usuario = repositorio.buscarPorId(usuarioId);
        if (usuario.temAluguelAtivo()) {
            throw new UsuarioComAluguelEmAndamentoException();
        }
        repositorio.remover(usuarioId);
    }

    private void validarEmailDisponivel(String email) {
        if (repositorio.emailJaCadastrado(email)) {
            throw new EmailJaCadastradoException();
        }
    }

    private void validarTipoPessoaFisicaSemCnpj(String tipo) {
        if (!"pessoaFisica".equals(tipo)) {
            throw new TipoInvalidoException();
        }
    }

    private void validarTipoPessoaJuridicaSemCpf(String tipo) {
        if (!"pessoaJuridica".equals(tipo)) {
            throw new TipoInvalidoException();
        }
    }

    private void validarAtributoEhMetodoPagamento(String atributo) {
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    private void alterarPlano(Usuario usuario, String novoPlano) {
        if (!PLANO_AVULSO.equals(novoPlano) && !PLANO_ASSINANTE.equals(novoPlano)) {
            throw new PlanoInvalidoException();
        }
        if (PLANO_AVULSO.equals(novoPlano)) {
            usuario.setPlano(PLANO_AVULSO, 0.0);
        } else {
            if (PLANO_ASSINANTE.equals(usuario.getPlano())) {
                usuario.setPlano(PLANO_ASSINANTE, usuario.getMensalidade());
            } else {
                usuario.setPlano(PLANO_ASSINANTE, fabrica.getMensalidadePadrao());
            }
        }
    }

    private void alterarSaldo(Usuario usuario, String valorStr) {
        if (!usuario.temMetodoPagamento()
                || !"saldoPreCarregado".equals(usuario.getMetodoPagamento().getTipo())) {
            throw new UsuarioNaoUsaSaldoException();
        }
        if (Formatador.estaVazio(valorStr)) {
            throw new ValorNuloException();
        }
        if (!Formatador.ehNumero(valorStr)) {
            throw new ValorDeveSerNumericoException();
        }
        double valor = Formatador.lerDinheiro(valorStr);
        if (valor <= 0) {
            throw new ValorDeveSerPositivoException();
        }
        usuario.getMetodoPagamento().adicionarSaldo(valor);
        usuario.setSaldoPreCarregadoHistorico(usuario.getMetodoPagamento().getSaldo());
    }
}

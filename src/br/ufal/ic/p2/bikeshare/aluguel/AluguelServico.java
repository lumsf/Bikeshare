package br.ufal.ic.p2.bikeshare.aluguel;

import java.time.Duration;
import java.time.LocalDateTime;
import br.ufal.ic.p2.bikeshare.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.bicicleta.BicicletaRepositorio;
import br.ufal.ic.p2.bikeshare.estacao.Estacao;
import br.ufal.ic.p2.bikeshare.estacao.EstacaoRepositorio;
import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.pagamento.PagamentoSaldoPreCarregado;
import br.ufal.ic.p2.bikeshare.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioRepositorio;
import br.ufal.ic.p2.bikeshare.util.DataUtil;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Serviço que gerencia as operações de negócio de retirada e devolução de bicicletas.
 */
public class AluguelServico {

    private final AluguelRepositorio aluguelRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final EstacaoRepositorio estacaoRepositorio;
    private final BicicletaRepositorio bicicletaRepositorio;

    public AluguelServico(AluguelRepositorio aluguelRepositorio,
                          UsuarioRepositorio usuarioRepositorio,
                          EstacaoRepositorio estacaoRepositorio,
                          BicicletaRepositorio bicicletaRepositorio) {
        this.aluguelRepositorio = aluguelRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.estacaoRepositorio = estacaoRepositorio;
        this.bicicletaRepositorio = bicicletaRepositorio;
    }

    /**
     * Realiza a retirada de uma bicicleta por um usuário em uma estação.
     */
    public String retirarBicicleta(String usuarioId, String estacaoId, String bicicletaId, String dataStr) {
        if (Formatador.estaVazio(usuarioId)) {
            throw new UsuarioIdNuloException();
        }
        Usuario usuario = usuarioRepositorio.buscarPorId(usuarioId);

        if (Formatador.estaVazio(estacaoId)) {
            throw new EstacaoIdNulaException();
        }
        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);

        if (Formatador.estaVazio(bicicletaId)) {
            throw new BicicletaIdNulaException();
        }
        Bicicleta bicicleta = bicicletaRepositorio.buscarPorId(bicicletaId);

        LocalDateTime dataRetirada = DataUtil.parseData(dataStr);

        if (usuario.temAluguelAtivo()) {
            throw new UsuarioJaPossuiAluguelEmAndamentoException();
        }

        if (!estacao.contemBicicleta(bicicleta.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }

        if (!bicicleta.podeSerDisponibilizada()) {
            throw new NivelBateriaInsuficienteException();
        }

        if (!usuario.temMetodoPagamento()) {
            throw new MetodoPagamentoNaoCadastradoException();
        }

        if (usuario.getMetodoPagamento() instanceof PagamentoSaldoPreCarregado) {
            if (usuario.getMetodoPagamento().getSaldo() <= 0.0) {
                throw new SaldoInsuficienteException();
            }
        }

        if ("inadimplente".equals(usuario.getEstado())) {
            throw new UsuarioInadimplenteException();
        }

        String aluguelId = aluguelRepositorio.gerarProximoId();
        Aluguel aluguel = new Aluguel(aluguelId, usuario.getId(), bicicleta.getId(), estacao.getId(), dataRetirada);
        aluguelRepositorio.adicionar(aluguel);

        estacao.retirarBicicleta(bicicleta.getId());
        bicicleta.setEstacaoId(null);
        bicicleta.setEstado("emUso");
        usuario.setAluguelAtivoId(aluguel.getId());

        return aluguelId;
    }

    /**
     * Realiza a devolução da bicicleta, calculando tarifas ou aplicando regras de cancelamento/expiração.
     */
    public void devolverBicicleta(String aluguelId, String estacaoId, String dataStr) {
        if (Formatador.estaVazio(aluguelId)) {
            throw new AluguelIdNuloException();
        }
        Aluguel aluguel = aluguelRepositorio.buscarPorId(aluguelId);

        if (Formatador.estaVazio(estacaoId)) {
            throw new EstacaoIdNulaException();
        }
        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);

        LocalDateTime dataDevolucao = DataUtil.parseData(dataStr);

        if (!"emAndamento".equals(aluguel.getEstado())) {
            throw new AluguelNaoEstaEmAndamentoException();
        }

        if (dataDevolucao.isBefore(aluguel.getInicio())) {
            throw new DataDevolucaoAnteriorRetiradaException();
        }

        if (!estacao.temVaga()) {
            throw new EstacaoCheiaException();
        }

        Usuario usuario = usuarioRepositorio.buscarPorId(aluguel.getUsuarioId());
        Bicicleta bicicleta = bicicletaRepositorio.buscarPorId(aluguel.getBicicletaId());

        long minutos = Duration.between(aluguel.getInicio(), dataDevolucao).toMinutes();

        if (minutos <= 2 && estacao.getId().equals(aluguel.getEstacaoOrigemId())) {
            aluguel.setEstado("cancelado");
            aluguel.setValorCobrado(0.0);
            aluguel.setFim(dataDevolucao);
            aluguel.setEstacaoDestinoId(estacao.getId());

            estacao.estacionarBicicleta(bicicleta.getId());
            bicicleta.setEstacaoId(estacao.getId());
            bicicleta.setEstado("disponivel");
            usuario.setAluguelAtivoId(null);
            return;
        }

        if (minutos > 1440) {
            aluguel.setEstado("expirado");
            aluguel.setValorCobrado(0.0);
            aluguel.setFim(dataDevolucao);
            aluguel.setEstacaoDestinoId(estacao.getId());

            estacao.estacionarBicicleta(bicicleta.getId());
            bicicleta.setEstacaoId(estacao.getId());
            bicicleta.setEstado("emManutencao");
            usuario.setEstado("inadimplente");
            usuario.adicionarPendencia(10.0);
            usuario.setAluguelAtivoId(null);
            return;
        }

        double tarifaBase;
        if ("assinante".equals(usuario.getPlano())) {
            long excedente = Math.max(0, minutos - 60);
            tarifaBase = excedente * 0.30;
        } else {
            tarifaBase = minutos * 0.50;
        }

        double adicionalEletrica = "eletrica".equals(bicicleta.getTipo()) ? minutos * 0.20 : 0.0;
        double valorTotal = tarifaBase + adicionalEletrica;

        if (usuario.getMetodoPagamento() instanceof PagamentoSaldoPreCarregado) {
            if (usuario.getMetodoPagamento().getSaldo() < valorTotal) {
                throw new SaldoInsuficienteException();
            }
            usuario.getMetodoPagamento().adicionarSaldo(-valorTotal);
            usuario.setSaldoPreCarregadoHistorico(usuario.getMetodoPagamento().getSaldo());
        }

        aluguel.setEstado("finalizado");
        aluguel.setValorCobrado(valorTotal);
        aluguel.setFim(dataDevolucao);
        aluguel.setEstacaoDestinoId(estacao.getId());

        estacao.estacionarBicicleta(bicicleta.getId());
        bicicleta.setEstacaoId(estacao.getId());
        bicicleta.setEstado("disponivel");
        usuario.setAluguelAtivoId(null);
    }

    public String getAtributoAluguel(String aluguelId, String atributo) {
        Aluguel aluguel = aluguelRepositorio.buscarPorId(aluguelId);
        if (Formatador.estaVazio(atributo)) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return aluguel.getEstado();
            case "usuario":
                return aluguel.getUsuarioId();
            case "bicicleta":
                return aluguel.getBicicletaId();
            case "estacaoOrigem":
                return aluguel.getEstacaoOrigemId();
            case "estacaoDestino":
                return aluguel.getEstacaoDestinoId();
            case "inicio":
                return aluguel.getInicioFormatado();
            case "fim":
                return aluguel.getFimFormatado();
            case "valorCobrado":
                return aluguel.getValorCobradoFormatado();
            default:
                throw new AtributoNaoExisteException();
        }
    }
}

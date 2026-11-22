package br.ufal.ic.p2.bikeshare.estacao;

import br.ufal.ic.p2.bikeshare.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.bicicleta.BicicletaConvencional;
import br.ufal.ic.p2.bikeshare.bicicleta.BicicletaEletrica;
import br.ufal.ic.p2.bikeshare.bicicleta.BicicletaRepositorio;
import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Serviço que implementa as regras de negócio para estações e suas bicicletas associadas.
 */
public class EstacaoServico {

    private final EstacaoRepositorio estacaoRepositorio;
    private final BicicletaRepositorio bicicletaRepositorio;

    public EstacaoServico(EstacaoRepositorio estacaoRepositorio, BicicletaRepositorio bicicletaRepositorio) {
        this.estacaoRepositorio = estacaoRepositorio;
        this.bicicletaRepositorio = bicicletaRepositorio;
    }

    public String criarEstacao(String nome, String endereco, String capacidadeStr) {
        if (Formatador.estaVazio(nome)) {
            throw new IllegalArgumentException("Nome nao pode ser nulo.");
        }
        if (Formatador.estaVazio(endereco)) {
            throw new EnderecoNuloException();
        }
        if (Formatador.estaVazio(capacidadeStr)) {
            throw new CapacidadeNulaException();
        }
        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeStr.trim());
        } catch (NumberFormatException e) {
            throw new CapacidadeDeveSerNumericaException();
        }
        if (capacidade <= 0) {
            throw new CapacidadeDeveSerPositivaException();
        }
        String id = estacaoRepositorio.gerarProximoId();
        Estacao estacao = new Estacao(id, nome.trim(), endereco.trim(), capacidade);
        estacaoRepositorio.adicionar(estacao);
        return id;
    }

    public int getVagasDisponiveis(String estacaoId) {
        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);
        return estacao.getVagasDisponiveis();
    }

    public int getBicicletasDisponiveis(String estacaoId) {
        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);
        int count = 0;
        for (String bikeId : estacao.getBicicletas()) {
            Bicicleta b = bicicletaRepositorio.buscarPorId(bikeId);
            if (b.estaDisponivel()) {
                count++;
            }
        }
        return count;
    }

    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo) {
        validarEstacao(estacaoId);
        if (Formatador.estaVazio(bicicletaId)) {
            throw new BicicletaIdNulaException();
        }
        if (bicicletaRepositorio.existe(bicicletaId)) {
            throw new BicicletaJaExisteException();
        }
        if ("eletrica".equals(tipo)) {
            throw new NivelBateriaNuloException();
        }
        if (!"convencional".equals(tipo)) {
            throw new TipoInvalidoException();
        }

        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);
        if (!estacao.temVaga()) {
            throw new EstacaoSemVagasDisponiveisException();
        }

        Bicicleta b = new BicicletaConvencional(bicicletaId.trim(), estacao.getId());
        bicicletaRepositorio.adicionar(b);
        estacao.estacionarBicicleta(b.getId());
    }

    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo, String nivelBateriaStr) {
        validarEstacao(estacaoId);
        if (Formatador.estaVazio(bicicletaId)) {
            throw new BicicletaIdNulaException();
        }
        if (bicicletaRepositorio.existe(bicicletaId)) {
            throw new BicicletaJaExisteException();
        }
        if ("convencional".equals(tipo)) {
            throw new BicicletaNaoPossuiBateriaException();
        }
        if (!"eletrica".equals(tipo)) {
            throw new TipoInvalidoException();
        }
        if (Formatador.estaVazio(nivelBateriaStr)) {
            throw new NivelBateriaNuloException();
        }
        int bateria;
        try {
            bateria = Integer.parseInt(nivelBateriaStr.trim());
        } catch (NumberFormatException e) {
            throw new NivelBateriaDeveSerNumericoException();
        }
        if (bateria < 0 || bateria > 100) {
            throw new NivelBateriaDeveEstarEntre0e100Exception();
        }

        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);
        if (!estacao.temVaga()) {
            throw new EstacaoSemVagasDisponiveisException();
        }

        Bicicleta b = new BicicletaEletrica(bicicletaId.trim(), estacao.getId(), bateria);
        bicicletaRepositorio.adicionar(b);
        estacao.estacionarBicicleta(b.getId());
    }

    public void removerBicicleta(String estacaoId, String bicicletaId) {
        Estacao estacao = estacaoRepositorio.buscarPorId(estacaoId);
        if (Formatador.estaVazio(bicicletaId)) {
            throw new BicicletaIdNulaException();
        }
        Bicicleta b = bicicletaRepositorio.buscarPorId(bicicletaId);
        if (!estacao.contemBicicleta(b.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }
        estacao.retirarBicicleta(b.getId());
        bicicletaRepositorio.remover(b.getId());
    }

    public String getAtributoBicicleta(String bicicletaId, String atributo) {
        Bicicleta b = bicicletaRepositorio.buscarPorId(bicicletaId);
        if (Formatador.estaVazio(atributo)) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "tipo":
                return b.getTipo();
            case "estado":
                return b.getEstado();
            case "estacao":
                return b.getEstacaoId();
            case "nivelBateria":
                return String.valueOf(b.getNivelBateria());
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String getEstadoBicicleta(String bicicletaId) {
        Bicicleta b = bicicletaRepositorio.buscarPorId(bicicletaId);
        return b.getEstado();
    }

    private void validarEstacao(String estacaoId) {
        if (Formatador.estaVazio(estacaoId)) {
            throw new EstacaoIdNulaException();
        }
        if (!estacaoRepositorio.existe(estacaoId)) {
            throw new EstacaoNaoExisteException();
        }
    }
}

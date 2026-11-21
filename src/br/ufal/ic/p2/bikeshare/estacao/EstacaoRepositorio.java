package br.ufal.ic.p2.bikeshare.estacao;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoIdNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class EstacaoRepositorio implements Serializable {

    private static final String ARQUIVO_DADOS = "relatorio/estacoes.dat";

    private final Map<String, Estacao> estacoes;
    private int proximoId;

    public EstacaoRepositorio() {
        this.estacoes = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public String gerarProximoId() {
        return "E" + proximoId;
    }

    public void adicionar(Estacao estacao) {
        estacoes.put(estacao.getId(), estacao);
        proximoId++;
    }

    public Estacao buscarPorId(String id) {
        if (Formatador.estaVazio(id)) {
            throw new EstacaoIdNulaException();
        }
        Estacao estacao = estacoes.get(id.trim());
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }

    public boolean existe(String id) {
        if (Formatador.estaVazio(id)) return false;
        return estacoes.containsKey(id.trim());
    }

    public List<Estacao> listarTodas() {
        return new ArrayList<>(estacoes.values());
    }

    public void salvar() {
        try {
            File arquivo = new File(ARQUIVO_DADOS);
            arquivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar estacoes: " + e.getMessage(), e);
        }
    }

    public static EstacaoRepositorio carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new EstacaoRepositorio();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (EstacaoRepositorio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new EstacaoRepositorio();
        }
    }

    public void zerar() {
        this.estacoes.clear();
        this.proximoId = 1;
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}

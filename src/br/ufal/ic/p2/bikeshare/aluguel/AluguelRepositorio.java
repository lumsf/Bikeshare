package br.ufal.ic.p2.bikeshare.aluguel;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import br.ufal.ic.p2.bikeshare.exceptions.AluguelIdNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Repositório responsável pela persistência e consulta dos registros de aluguéis.
 */
public class AluguelRepositorio implements Serializable {

    private static final String ARQUIVO_DADOS = "dados/alugueis.dat";

    private final Map<String, Aluguel> alugueis;
    private int proximoId;

    public AluguelRepositorio() {
        this.alugueis = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public String gerarProximoId() {
        return "A" + proximoId;
    }

    public void adicionar(Aluguel aluguel) {
        alugueis.put(aluguel.getId(), aluguel);
        proximoId++;
    }

    public Aluguel buscarPorId(String id) {
        if (Formatador.estaVazio(id)) {
            throw new AluguelIdNuloException();
        }
        Aluguel aluguel = alugueis.get(id.trim());
        if (aluguel == null) {
            throw new AluguelNaoExisteException();
        }
        return aluguel;
    }

    public List<Aluguel> listarPorUsuario(String usuarioId) {
        List<Aluguel> lista = new ArrayList<>();
        for (Aluguel a : alugueis.values()) {
            if (a.getUsuarioId().equals(usuarioId)) {
                lista.add(a);
            }
        }
        return lista;
    }

    public void salvar() {
        try {
            File arquivo = new File(ARQUIVO_DADOS);
            if (arquivo.getParentFile() != null) {
                arquivo.getParentFile().mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar alugueis: " + e.getMessage(), e);
        }
    }

    public static AluguelRepositorio carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new AluguelRepositorio();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (AluguelRepositorio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new AluguelRepositorio();
        }
    }

    public void zerar() {
        this.alugueis.clear();
        this.proximoId = 1;
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}

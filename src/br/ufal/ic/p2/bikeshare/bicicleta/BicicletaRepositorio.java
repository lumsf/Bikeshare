package br.ufal.ic.p2.bikeshare.bicicleta;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaIdNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class BicicletaRepositorio implements Serializable {

    private static final String ARQUIVO_DADOS = "relatorio/bicicletas.dat";

    private final Map<String, Bicicleta> bicicletas;

    public BicicletaRepositorio() {
        this.bicicletas = new LinkedHashMap<>();
    }

    public void adicionar(Bicicleta bicicleta) {
        bicicletas.put(bicicleta.getId(), bicicleta);
    }

    public void remover(String id) {
        bicicletas.remove(id);
    }

    public Bicicleta buscarPorId(String id) {
        if (Formatador.estaVazio(id)) {
            throw new BicicletaIdNulaException();
        }
        Bicicleta bike = bicicletas.get(id.trim());
        if (bike == null) {
            throw new BicicletaNaoExisteException();
        }
        return bike;
    }

    public boolean existe(String id) {
        if (Formatador.estaVazio(id)) return false;
        return bicicletas.containsKey(id.trim());
    }

    public List<Bicicleta> listarTodas() {
        return new ArrayList<>(bicicletas.values());
    }

    public void salvar() {
        try {
            File arquivo = new File(ARQUIVO_DADOS);
            arquivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar bicicletas: " + e.getMessage(), e);
        }
    }

    public static BicicletaRepositorio carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new BicicletaRepositorio();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (BicicletaRepositorio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new BicicletaRepositorio();
        }
    }

    public void zerar() {
        this.bicicletas.clear();
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}

package br.ufal.ic.p2.bikeshare.estacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma estação física do sistema com capacidade definida para estacionar bicicletas.
 */
public class Estacao implements Serializable {

    private final String id;
    private String nome;
    private String endereco;
    private int capacidade;
    private final List<String> bicicletas;

    public Estacao(String id, String nome, String endereco, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidade;
        this.bicicletas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<String> getBicicletas() {
        return bicicletas;
    }

    public boolean temVaga() {
        return bicicletas.size() < capacidade;
    }

    public int getVagasDisponiveis() {
        return capacidade - bicicletas.size();
    }

    public void estacionarBicicleta(String bicicletaId) {
        bicicletas.add(bicicletaId);
    }

    public void retirarBicicleta(String bicicletaId) {
        bicicletas.remove(bicicletaId);
    }

    public boolean contemBicicleta(String bicicletaId) {
        return bicicletas.contains(bicicletaId);
    }
}

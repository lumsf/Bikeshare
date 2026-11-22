package br.ufal.ic.p2.bikeshare.bicicleta;

import java.io.Serializable;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoPossuiBateriaException;

/**
 * Classe base para os veículos do sistema de compartilhamento.
 */
public abstract class Bicicleta implements Serializable {

    private final String id;
    private String estacaoId;
    private String estado;

    public Bicicleta(String id, String estacaoId) {
        this.id = id;
        this.estacaoId = estacaoId;
        this.estado = "disponivel";
    }

    public String getId() {
        return id;
    }

    public String getEstacaoId() {
        return estacaoId != null ? estacaoId : "nenhuma";
    }

    public void setEstacaoId(String estacaoId) {
        this.estacaoId = estacaoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public abstract String getTipo();

    public abstract boolean podeSerDisponibilizada();

    public boolean estaDisponivel() {
        return "disponivel".equals(this.estado) && podeSerDisponibilizada();
    }

    public int getNivelBateria() {
        throw new BicicletaNaoPossuiBateriaException();
    }
}

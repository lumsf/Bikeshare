package br.ufal.ic.p2.bikeshare.bicicleta;

public class BicicletaEletrica extends Bicicleta {

    private int nivelBateria;

    public BicicletaEletrica(String id, String estacaoId, int nivelBateria) {
        super(id, estacaoId);
        this.nivelBateria = nivelBateria;
    }

    @Override
    public String getTipo() {
        return "eletrica";
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }

    public void setNivelBateria(int nivelBateria) {
        this.nivelBateria = nivelBateria;
    }

    @Override
    public boolean podeSerDisponibilizada() {
        return nivelBateria >= 15;
    }
}

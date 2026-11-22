package br.ufal.ic.p2.bikeshare.bicicleta;

/**
 * Representa uma bicicleta do tipo convencional (mecânica).
 */
public class BicicletaConvencional extends Bicicleta {

    public BicicletaConvencional(String id, String estacaoId) {
        super(id, estacaoId);
    }

    @Override
    public String getTipo() {
        return "convencional";
    }

    @Override
    public boolean podeSerDisponibilizada() {
        return true;
    }
}

package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoPossuiBateriaException extends RuntimeException {
    public BicicletaNaoPossuiBateriaException() {
        super("Bicicleta nao possui bateria.");
    }
}

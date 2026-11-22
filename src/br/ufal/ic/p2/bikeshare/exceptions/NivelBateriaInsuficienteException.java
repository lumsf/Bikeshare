package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaInsuficienteException extends RuntimeException {
    public NivelBateriaInsuficienteException() {
        super("Nivel de bateria insuficiente.");
    }
}

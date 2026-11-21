package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaDeveSerNumericoException extends RuntimeException {
    public NivelBateriaDeveSerNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}

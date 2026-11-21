package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaDeveEstarEntre0e100Exception extends RuntimeException {
    public NivelBateriaDeveEstarEntre0e100Exception() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}

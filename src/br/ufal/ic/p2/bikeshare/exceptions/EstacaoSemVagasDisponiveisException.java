package br.ufal.ic.p2.bikeshare.exceptions;

public class EstacaoSemVagasDisponiveisException extends RuntimeException {
    public EstacaoSemVagasDisponiveisException() {
        super("Estacao sem vagas disponiveis.");
    }
}

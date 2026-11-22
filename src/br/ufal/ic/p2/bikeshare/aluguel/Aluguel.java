package br.ufal.ic.p2.bikeshare.aluguel;

import java.io.Serializable;
import java.time.LocalDateTime;
import br.ufal.ic.p2.bikeshare.exceptions.AluguelAindaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.util.DataUtil;
import br.ufal.ic.p2.bikeshare.util.Formatador;

/**
 * Representa uma operação de aluguel (corrida) de bicicleta realizada por um usuário.
 */
public class Aluguel implements Serializable {

    private final String id;
    private final String usuarioId;
    private final String bicicletaId;
    private final String estacaoOrigemId;
    private final LocalDateTime inicio;

    private String estado;
    private String estacaoDestinoId;
    private LocalDateTime fim;
    private Double valorCobrado;

    public Aluguel(String id, String usuarioId, String bicicletaId, String estacaoOrigemId, LocalDateTime inicio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.inicio = inicio;
        this.estado = "emAndamento";
    }

    public String getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getBicicletaId() {
        return bicicletaId;
    }

    public String getEstacaoOrigemId() {
        return estacaoOrigemId;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public String getInicioFormatado() {
        return DataUtil.formatarData(inicio);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstacaoDestinoId() {
        if ("emAndamento".equals(estado)) {
            throw new AluguelAindaEmAndamentoException();
        }
        return estacaoDestinoId;
    }

    public void setEstacaoDestinoId(String estacaoDestinoId) {
        this.estacaoDestinoId = estacaoDestinoId;
    }

    public String getFimFormatado() {
        if ("emAndamento".equals(estado)) {
            throw new AluguelAindaEmAndamentoException();
        }
        return DataUtil.formatarData(fim);
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public Double getValorCobrado() {
        if ("emAndamento".equals(estado)) {
            throw new AluguelAindaEmAndamentoException();
        }
        return valorCobrado;
    }

    public String getValorCobradoFormatado() {
        if ("emAndamento".equals(estado)) {
            throw new AluguelAindaEmAndamentoException();
        }
        return Formatador.formatarDinheiro(valorCobrado != null ? valorCobrado : 0.0);
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }
}

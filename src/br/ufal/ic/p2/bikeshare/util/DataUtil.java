package br.ufal.ic.p2.bikeshare.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import br.ufal.ic.p2.bikeshare.exceptions.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.exceptions.DataNulaException;

/**
 * Utilitário para conversão e formatação de datas e horários no formato padrão do sistema.
 */
public class DataUtil {

    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral('/')
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral('/')
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public static LocalDateTime parseData(String dataStr) {
        if (Formatador.estaVazio(dataStr)) {
            throw new DataNulaException();
        }
        String texto = dataStr.trim();
        try {
            return LocalDateTime.parse(texto, PARSER);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
    }

    public static String formatarData(LocalDateTime dt) {
        if (dt == null) return null;
        int dia = dt.getDayOfMonth();
        int mes = dt.getMonthValue();
        int ano = dt.getYear();
        int hora = dt.getHour();
        int min = dt.getMinute();
        String minStr = min < 10 ? "0" + min : String.valueOf(min);
        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minStr;
    }
}

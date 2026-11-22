package br.ufal.ic.p2.bikeshare.util;

/**
 * Utilitário para validação e formatação de dados textuais e monetários.
 */
public class Formatador {

    public static boolean estaVazio(String texto) {
        if (texto == null) {
            return true;
        }
        String semEspacos = texto.trim();  
        return semEspacos.length() == 0;
    }

    public static String formatarDinheiro(double valor) {
        long centavos = Math.round(valor * 100);   
        long reais = centavos / 100;               
        long resto = centavos % 100;               

        String textoResto = "" + resto;
        if (resto < 10) {
            textoResto = "0" + resto;             
        }
        return reais + "," + textoResto;           
    }

    public static boolean ehNumero(String texto) {
        if (texto == null) {
            return false;
        }
        texto = texto.trim();
        if (texto.length() == 0) {
            return false;
        }

        int inicio = 0;
        if (texto.charAt(0) == '-') {
            inicio = 1;                            
        }

        boolean temDigito = false;
        boolean temSeparador = false;
        for (int i = inicio; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c >= '0' && c <= '9') {
                temDigito = true;
            } else if ((c == ',' || c == '.') && !temSeparador) {
                temSeparador = true;
            } else {
                return false;                      
            }
        }
        return temDigito;
    }

    public static double lerDinheiro(String texto) {
        String comPonto = texto.trim().replace(',', '.');   
        return Double.parseDouble(comPonto);
    }
}

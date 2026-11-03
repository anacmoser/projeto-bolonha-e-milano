package bolonhaemilano;

/**
 * Arrecada impostos e define taxa e nível de justiça (Etapa 5).
 * Não guarda nenhuma Cidade: ela chega como parâmetro (dependência).
 */
public class Governo {

    private static final int TAXA_MAXIMA = 50;
    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    /** base = servos x taxa / 100, multiplicada pelo fator da justiça atual. */
    public int arrecadarImpostos(Cidade cidade) {
        int base = cidade.getServos() * cidade.getTaxaImposto() / 100;
        double multiplicador = multiplicadorPara(cidade.getNivelJustica());
        int arrecadado = (int) (base * multiplicador);

        cidade.adicionarFlorins(arrecadado);
        return arrecadado;
    }

    /** Aceita taxas de 0 a 50; fora disso devolve false sem alterar nada. */
    public boolean definirTaxaImposto(Cidade cidade, int taxa) {
        if (taxa < 0 || taxa > TAXA_MAXIMA) {
            return false;
        }
        cidade.setTaxaImposto(taxa);
        return true;
    }

    /** Aceita apenas "Justa", "Moderada" ou "Severa". */
    public boolean definirJustica(Cidade cidade, String nivel) {
        if (!"Justa".equals(nivel) && !"Moderada".equals(nivel) && !"Severa".equals(nivel)) {
            return false;
        }
        cidade.setNivelJustica(nivel);
        return true;
    }

    private double multiplicadorPara(String nivel) {
        if ("Justa".equals(nivel)) {
            return MULTIPLICADOR_JUSTA;
        }
        if ("Severa".equals(nivel)) {
            return MULTIPLICADOR_SEVERA;
        }
        return MULTIPLICADOR_MODERADA;
    }
}

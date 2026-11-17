package bolonhaemilano;

/**
 * Calcula o prestígio de uma cidade e promove o governante (Etapa 7).
 * Não guarda nenhuma Cidade: ela chega como parâmetro (dependência).
 */
public class Nobreza {

    private static final int PESO_CONSTRUCAO = 10;
    private static final int PESO_SOLDADO = 2;
    private static final int PESO_SERVO = 1;
    private static final int HECTARES_POR_PONTO = 100;
    private static final int[] PRESTIGIO_MINIMO = {0, 400, 550, 700, 850, 1000};

    /** prestígio = construções x 10 + soldados x 2 + servos + terras / 100. Florins não contam. */
    public int calcularPrestigio(Cidade cidade) {
        return cidade.getConstrucoes() * PESO_CONSTRUCAO
                + cidade.getSoldados() * PESO_SOLDADO
                + cidade.getServos() * PESO_SERVO
                + cidade.getTerras() / HECTARES_POR_PONTO;
    }

    /** Prestígio mínimo do próximo título, ou -1 se o jogador já é Rei/Rainha. */
    public int prestigioParaProximoTitulo(Jogador jogador) {
        if (jogador.ehMonarca()) {
            return -1;
        }
        return PRESTIGIO_MINIMO[jogador.getNivelTitulo() + 1];
    }

    /** Promove o dono da cidade se o prestígio alcança o próximo título (no máximo 1 por chamada). */
    public boolean verificarPromocao(Cidade cidade) {
        Jogador dono = cidade.getDono();
        int necessario = prestigioParaProximoTitulo(dono);
        if (necessario < 0) {
            return false;
        }
        if (calcularPrestigio(cidade) < necessario) {
            return false;
        }
        return dono.promover();
    }
}

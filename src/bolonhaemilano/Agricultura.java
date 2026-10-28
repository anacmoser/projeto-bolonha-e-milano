package bolonhaemilano;

/**
 * Processa a colheita e a distribuição de alimentos (Etapa 4).
 * Não guarda nenhuma Cidade: ela chega como parâmetro (dependência).
 */
public class Agricultura {

    private static final int HECTARES_POR_SERVO = 5;
    private static final int RENDIMENTO_POR_HECTARE = 2;
    private static final int SERVOS_POR_GRAO = 5;
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    /**
     * Colhe: hectares = min(terras, servos x 5); sementes = hectares / 2
     * (a área é reduzida se faltar grão); produção = hectares x 2.
     * Devolve os hectares realmente cultivados.
     */
    public int processarColheita(Cidade cidade) {
        int hectares = Math.min(cidade.getTerras(), cidade.getServos() * HECTARES_POR_SERVO);
        int sementes = hectares / 2; // 1 unidade de semente para cada 2 hectares

        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * 2;
        }

        cidade.removerGraos(sementes);
        int producao = hectares * RENDIMENTO_POR_HECTARE;
        cidade.adicionarGraos(producao);
        return hectares;
    }

    /**
     * Distribui um percentual (20 a 80) dos grãos à população.
     * Devolve false, sem alterar nada, se o percentual for inválido.
     */
    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < PERCENTUAL_MINIMO || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }

        int alimentoDistribuido = (cidade.getGraos() * percentual) / 100;
        cidade.removerGraos(alimentoDistribuido);

        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;
        if (alimentoDistribuido >= necessidade) {
            int crescimento = cidade.getServos() * CRESCIMENTO_PERCENTUAL / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }
}

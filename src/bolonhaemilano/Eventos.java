package bolonhaemilano;

import java.util.Random;

/**
 * Extra do projeto: eventos aleatórios opcionais no fim do ano (seca, ratos,
 * peste, boa safra, invasão). Só usa os métodos públicos da Cidade, então
 * nenhum recurso fica negativo. Vem desligado por padrão.
 */
public class Eventos {

    private static final int PERCENTUAL_SECA = 30;
    private static final int PERCENTUAL_RATOS = 10;
    private static final int PERCENTUAL_PESTE = 10;
    private static final int PERCENTUAL_BOA_SAFRA = 20;
    private static final int PERCENTUAL_SAQUE = 10;
    private static final int SOLDADOS_PARA_REPELIR = 20;

    private final Random sorteio;

    public Eventos() {
        this(new Random());
    }

    /** Permite injetar um Random para testar com resultados previsíveis. */
    public Eventos(Random sorteio) {
        this.sorteio = sorteio;
    }

    /** Sorteia (ou não) um evento para a cidade; devolve a descrição, ou null se nada aconteceu. */
    public String sortear(Cidade cidade) {
        int dado = sorteio.nextInt(100);

        if (dado < 12) {
            int perda = cidade.getGraos() * PERCENTUAL_SECA / 100;
            cidade.removerGraos(perda);
            return "Seca! A colheita sofreu e a cidade perdeu " + perda + " graos.";
        }
        if (dado < 24) {
            int perda = cidade.getGraos() * PERCENTUAL_RATOS / 100;
            cidade.removerGraos(perda);
            return "Ratos nos celeiros! Perdemos " + perda + " graos.";
        }
        if (dado < 34) {
            int mortos = cidade.getServos() * PERCENTUAL_PESTE / 100;
            cidade.removerServos(mortos);
            return "Peste! " + mortos + " servos morreram.";
        }
        if (dado < 46) {
            int ganho = cidade.getGraos() * PERCENTUAL_BOA_SAFRA / 100;
            cidade.adicionarGraos(ganho);
            return "Boa safra! A cidade ganhou " + ganho + " graos.";
        }
        if (dado < 56) {
            if (cidade.getSoldados() >= SOLDADOS_PARA_REPELIR) {
                return "Invasores foram repelidos pelos soldados da cidade!";
            }
            int saque = cidade.getFlorins() * PERCENTUAL_SAQUE / 100;
            cidade.removerFlorins(saque);
            return "Invasao! Com poucos soldados, a cidade perdeu " + saque + " florins.";
        }
        return null;
    }
}

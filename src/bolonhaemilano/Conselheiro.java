package bolonhaemilano;

/**
 * Extra do projeto: lê a situação da cidade e sugere o próximo passo para
 * conquistar o próximo título, usando as mesmas fórmulas da Nobreza.
 */
public class Conselheiro {

    private static final int PRESTIGIO_POR_CONSTRUCAO = 10;
    private static final int CUSTO_CONSTRUCAO = 20;

    public String aconselhar(Cidade cidade, Nobreza nobreza) {
        StringBuilder texto = new StringBuilder();
        Jogador dono = cidade.getDono();
        int prestigio = nobreza.calcularPrestigio(cidade);
        int necessario = nobreza.prestigioParaProximoTitulo(dono);

        if (necessario < 0) {
            texto.append("Majestade, o trono ja e vosso. Mantende o reino em paz!");
            return texto.toString();
        }

        int falta = necessario - prestigio;
        if (falta <= 0) {
            texto.append("Seu prestigio (" + prestigio + ") ja alcanca o proximo titulo ("
                    + necessario + "). Encerre o turno e feche o ano para ser promovido.");
        } else {
            int construcoes = (falta + PRESTIGIO_POR_CONSTRUCAO - 1) / PRESTIGIO_POR_CONSTRUCAO;
            int custo = construcoes * CUSTO_CONSTRUCAO;
            texto.append("Prestigio " + prestigio + " de " + necessario + ": faltam " + falta + " pontos. ");
            if (cidade.getFlorins() >= custo) {
                texto.append("Construa " + construcoes + " construcoes (" + custo
                        + " florins) e o proximo titulo estara ao seu alcance.");
            } else {
                int possiveis = cidade.getFlorins() / CUSTO_CONSTRUCAO;
                texto.append("Voce tem " + cidade.getFlorins() + " florins: da para construir " + possiveis
                        + " agora (+" + (possiveis * PRESTIGIO_POR_CONSTRUCAO) + " pontos). Arrecade impostos e colha para juntar o resto.");
            }
        }

        if (!cidade.isColheitaFeita()) {
            texto.append("\nLembrete: voce ainda nao colheu neste ano.");
        }
        if (!cidade.isImpostosArrecadados()) {
            texto.append("\nLembrete: voce ainda nao arrecadou os impostos deste ano.");
        }
        return texto.toString();
    }
}

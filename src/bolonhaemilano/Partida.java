package bolonhaemilano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controla a partida: de 1 a 4 cidades, de quem é a vez, o fim do ano,
 * o fim do jogo e o vencedor (Etapa 7).
 */
public class Partida {

    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;

    private final List<Cidade> cidades = new ArrayList<>();
    private int indiceAtual = 0;

    /** Coloca a cidade na partida; recusa a quinta cidade. */
    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return Collections.unmodifiableList(cidades);
    }

    public Cidade getCidadeAtual() {
        return cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        return cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    /** Passa a vez. Devolve true se o último jogador acabou de encerrar (fim do ano). */
    public boolean passarTurno() {
        indiceAtual++;
        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true; // o ano acabou
        }
        return false;
    }

    /** Fecha o ano: avança todas as cidades e promove quem alcançou o prestígio. */
    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    /** Termina se alguém virou Rei/Rainha ou se o ano passou de 1410. */
    public boolean terminou() {
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }
        return getAnoAtual() > ANO_FINAL;
    }

    /** Vence o título mais alto; em caso de empate, o maior prestígio. */
    public Cidade determinarVencedor(Nobreza nobreza) {
        Cidade vencedora = cidades.get(0);
        for (Cidade cidade : cidades) {
            int nivel = cidade.getDono().getNivelTitulo();
            int nivelVencedora = vencedora.getDono().getNivelTitulo();
            if (nivel > nivelVencedora
                    || (nivel == nivelVencedora
                        && nobreza.calcularPrestigio(cidade) > nobreza.calcularPrestigio(vencedora))) {
                vencedora = cidade;
            }
        }
        return vencedora;
    }
}

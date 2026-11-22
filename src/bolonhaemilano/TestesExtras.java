package bolonhaemilano;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Testes dos extras (Persistencia, Eventos e Conselheiro). Separado da classe
 * Testes para não mudar o resultado esperado "Aprovados: 37".
 *
 *   java -cp bin bolonhaemilano.TestesExtras
 */
public class TestesExtras {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("===== TESTES DOS EXTRAS =====");
        testarPersistencia();
        testarEventos();
        testarConselheiro();

        System.out.println();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK]    " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    /** Random que sempre devolve o mesmo número, para eventos previsíveis. */
    private static Random dadoFixo(int valor) {
        return new Random() {
            @Override
            public int nextInt(int limite) {
                return valor;
            }
        };
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarPersistencia() throws IOException {
        System.out.println();
        System.out.println("-- Persistencia --");
        Partida partida = new Partida();
        Cidade ana = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade beto = new Cidade("Milano", new Jogador("Beto"));
        partida.adicionarCidade(ana);
        partida.adicionarCidade(beto);
        ana.adicionarConstrucoes(7);
        ana.removerFlorins(123);
        ana.marcarColheitaFeita();
        ana.getDono().promover();
        ana.setNivelJustica("Severa");
        ana.setTaxaImposto(25);
        partida.passarTurno(); // agora e a vez de Beto

        Path arquivo = Files.createTempFile("bolonha-teste", ".sav");
        try {
            Persistencia.salvar(partida, arquivo);
            Partida lida = Persistencia.carregar(arquivo);
            Cidade a = lida.getCidades().get(0);

            verificar("carregar devolve as 2 cidades", lida.getCidades().size() == 2);
            verificar("a vez atual e preservada (Milano)", "Milano".equals(lida.getCidadeAtual().getNome()));
            verificar("florins e construcoes sao preservados", a.getFlorins() == 877 && a.getConstrucoes() == 7);
            verificar("titulo e justica sao preservados", a.getDono().getNivelTitulo() == 1 && "Severa".equals(a.getNivelJustica()));
            verificar("taxa de imposto e preservada", a.getTaxaImposto() == 25);
            verificar("indicador da colheita e preservado", a.isColheitaFeita() && !a.isImpostosArrecadados());

            Files.write(arquivo, "isto nao e um save".getBytes());
            boolean recusou = false;
            try {
                Persistencia.carregar(arquivo);
            } catch (IllegalArgumentException e) {
                recusou = true;
            }
            verificar("arquivo invalido e recusado", recusou);
        } finally {
            Files.deleteIfExists(arquivo);
        }
    }

    private static void testarEventos() {
        System.out.println();
        System.out.println("-- Eventos --");

        Cidade seca = novaCidade();
        new Eventos(dadoFixo(5)).sortear(seca);
        verificar("seca tira 30% dos graos (500 -> 350)", seca.getGraos() == 350);

        Cidade peste = novaCidade();
        new Eventos(dadoFixo(30)).sortear(peste);
        verificar("peste tira 10% dos servos (200 -> 180)", peste.getServos() == 180);

        Cidade invadida = novaCidade();
        new Eventos(dadoFixo(50)).sortear(invadida);
        verificar("invasao sem defesa saqueia 10% dos florins", invadida.getFlorins() == 900);

        Cidade defendida = novaCidade();
        defendida.adicionarSoldados(20);
        new Eventos(dadoFixo(50)).sortear(defendida);
        verificar("20 soldados repelem a invasao", defendida.getFlorins() == 1000);

        Cidade calma = novaCidade();
        verificar("dado alto: nenhum evento", new Eventos(dadoFixo(99)).sortear(calma) == null);
    }

    private static void testarConselheiro() {
        System.out.println();
        System.out.println("-- Conselheiro --");
        Conselheiro conselheiro = new Conselheiro();
        Nobreza nobreza = new Nobreza();

        String dica = conselheiro.aconselhar(novaCidade(), nobreza);
        verificar("cidade nova: faltam 100 pontos", dica.contains("faltam 100 pontos"));
        verificar("... e sugere 10 construcoes (200 florins)", dica.contains("10 construcoes (200 florins)"));

        Cidade pobre = novaCidade();
        pobre.removerFlorins(1000);
        verificar("sem florins: avisa que nao da para construir",
                conselheiro.aconselhar(pobre, nobreza).contains("construir 0 agora"));

        Cidade rica = novaCidade();
        rica.adicionarConstrucoes(50);
        verificar("prestigio suficiente: manda encerrar o turno",
                conselheiro.aconselhar(rica, nobreza).contains("Encerre o turno"));
    }
}

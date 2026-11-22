package bolonhaemilano;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Extra do projeto: salva e carrega a partida em um arquivo de texto simples
 * (linhas chave=valor). A partida é reconstruída só com os métodos públicos
 * das classes do jogo, sem alterar nenhuma regra.
 */
public class Persistencia {

    private static final String CABECALHO = "BOLONHA_E_MILANO_SAVE_1";

    public static void salvar(Partida partida, Path arquivo) throws IOException {
        List<String> linhas = new ArrayList<>();
        linhas.add(CABECALHO);
        linhas.add("indiceAtual=" + partida.getCidades().indexOf(partida.getCidadeAtual()));
        for (Cidade c : partida.getCidades()) {
            linhas.add("[cidade]");
            linhas.add("nome=" + c.getNome());
            linhas.add("jogador=" + c.getDono().getNome());
            linhas.add("nivelTitulo=" + c.getDono().getNivelTitulo());
            linhas.add("ano=" + c.getAnoAtual());
            linhas.add("florins=" + c.getFlorins());
            linhas.add("terras=" + c.getTerras());
            linhas.add("graos=" + c.getGraos());
            linhas.add("servos=" + c.getServos());
            linhas.add("taxaImposto=" + c.getTaxaImposto());
            linhas.add("nivelJustica=" + c.getNivelJustica());
            linhas.add("soldados=" + c.getSoldados());
            linhas.add("construcoes=" + c.getConstrucoes());
            linhas.add("colheitaFeita=" + c.isColheitaFeita());
            linhas.add("impostosArrecadados=" + c.isImpostosArrecadados());
        }
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    /** Lê o arquivo e reconstrói a partida; lança IllegalArgumentException se o conteúdo for inválido. */
    public static Partida carregar(Path arquivo) throws IOException {
        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        if (linhas.isEmpty() || !CABECALHO.equals(linhas.get(0).trim())) {
            throw new IllegalArgumentException("arquivo de save invalido");
        }

        int indice = 0;
        List<Map<String, String>> blocos = new ArrayList<>();
        Map<String, String> atual = null;
        for (int i = 1; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) {
                continue;
            }
            if (linha.equals("[cidade]")) {
                atual = new HashMap<>();
                blocos.add(atual);
                continue;
            }
            int igual = linha.indexOf('=');
            if (igual < 0) {
                throw new IllegalArgumentException("linha invalida no save: " + linha);
            }
            String chave = linha.substring(0, igual);
            String valor = linha.substring(igual + 1);
            if (atual == null) {
                if (chave.equals("indiceAtual")) {
                    indice = Integer.parseInt(valor);
                }
            } else {
                atual.put(chave, valor);
            }
        }

        if (blocos.isEmpty() || blocos.size() > Partida.MAXIMO_JOGADORES) {
            throw new IllegalArgumentException("numero de cidades invalido no save");
        }
        if (indice < 0 || indice >= blocos.size()) {
            throw new IllegalArgumentException("vez atual invalida no save");
        }

        Partida partida = new Partida();
        for (Map<String, String> bloco : blocos) {
            partida.adicionarCidade(reconstruir(bloco));
        }
        for (int i = 0; i < indice; i++) {
            partida.passarTurno();
        }
        return partida;
    }

    private static Cidade reconstruir(Map<String, String> m) {
        Jogador jogador = new Jogador(texto(m, "jogador"));
        int nivel = inteiro(m, "nivelTitulo");
        if (nivel < 0 || nivel > 5) {
            throw new IllegalArgumentException("titulo invalido no save");
        }
        for (int i = 0; i < nivel; i++) {
            jogador.promover();
        }

        Cidade c = new Cidade(texto(m, "nome"), jogador);
        int ano = inteiro(m, "ano");
        if (ano < 1400 || ano > 1411) {
            throw new IllegalArgumentException("ano invalido no save");
        }
        while (c.getAnoAtual() < ano) {
            c.avancarAno();
        }

        c.removerFlorins(c.getFlorins());
        c.adicionarFlorins(naoNegativo(m, "florins"));
        c.removerTerras(c.getTerras());
        c.adicionarTerras(naoNegativo(m, "terras"));
        c.removerGraos(c.getGraos());
        c.adicionarGraos(naoNegativo(m, "graos"));
        c.removerServos(c.getServos());
        c.adicionarServos(naoNegativo(m, "servos"));
        c.removerSoldados(c.getSoldados());
        c.adicionarSoldados(naoNegativo(m, "soldados"));
        c.removerConstrucoes(c.getConstrucoes());
        c.adicionarConstrucoes(naoNegativo(m, "construcoes"));

        int taxa = inteiro(m, "taxaImposto");
        if (taxa < 0 || taxa > 50) {
            throw new IllegalArgumentException("taxa de imposto invalida no save");
        }
        c.setTaxaImposto(taxa);

        String justica = texto(m, "nivelJustica");
        if (!justica.equals("Justa") && !justica.equals("Moderada") && !justica.equals("Severa")) {
            throw new IllegalArgumentException("nivel de justica invalido no save");
        }
        c.setNivelJustica(justica);

        if (Boolean.parseBoolean(texto(m, "colheitaFeita"))) {
            c.marcarColheitaFeita();
        }
        if (Boolean.parseBoolean(texto(m, "impostosArrecadados"))) {
            c.marcarImpostosArrecadados();
        }
        return c;
    }

    private static String texto(Map<String, String> m, String chave) {
        String valor = m.get(chave);
        if (valor == null) {
            throw new IllegalArgumentException("campo ausente no save: " + chave);
        }
        return valor;
    }

    private static int inteiro(Map<String, String> m, String chave) {
        return Integer.parseInt(texto(m, chave));
    }

    private static int naoNegativo(Map<String, String> m, String chave) {
        int valor = inteiro(m, chave);
        if (valor < 0) {
            throw new IllegalArgumentException("valor negativo no save: " + chave);
        }
        return valor;
    }
}

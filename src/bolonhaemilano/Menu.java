package bolonhaemilano;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Menu principal do jogo. Controla o laço principal e delega o trabalho às
 * classes certas; as regras do jogo moram nas classes de serviço, não aqui.
 */
public class Menu {

    private static final String[] CIDADES_PADRAO = {"Bolonha", "Milano", "Florenca", "Veneza"};

    private final Scanner scanner = new Scanner(System.in);
    private boolean emExecucao = true;
    private Partida partida;
    private Cidade cidade; // a cidade de quem está jogando agora
    private final Mercado mercado = new Mercado();
    private final Agricultura agricultura = new Agricultura();
    private final Governo governo = new Governo();
    private final Construcoes construcoes = new Construcoes();
    private final Nobreza nobreza = new Nobreza();
    private static final Path ARQUIVO_SAVE = Paths.get("bolonha-e-milano.sav");
    private final Eventos eventos = new Eventos();
    private final Conselheiro conselheiro = new Conselheiro();
    private boolean eventosAtivos = false;

    /** Laço principal: repete até o jogador escolher sair. */
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    /** Apenas imprime o menu (com o ano e a vez de quem joga); não lê nada do teclado. */
    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO");
        System.out.println(" Um jogo de estrategia economica medieval");
        if (partida != null) {
            System.out.println(" Ano " + partida.getAnoAtual() + " | Vez de: " + descricaoDaVez());
        }
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("10 - Extras");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Lê uma opção do menu; no fim da entrada devolve 0 (sair / voltar). */
    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0;
        }
        return lerInteiro();
    }

    /** Lê uma quantidade (imprime o pedido antes, no próprio método que chama). */
    private int lerQuantidade() {
        return lerInteiro();
    }

    /** Lê uma linha e converte para inteiro; devolve -1 se for inválida. */
    private int lerInteiro() {
        if (!scanner.hasNextLine()) {
            return -1;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Avisa quando ainda não existe partida; devolve true se o jogo está em andamento. */
    private boolean exigirJogoEmAndamento() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    private String lerTexto() {
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
                abrirGoverno();
                break;
            case 6:
                abrirConstrucoes();
                break;
            case 7:
                encerrarTurno();
                break;
            case 8:
                exibirClassificacao();
                break;
            case 9:
                exibirSobre();
                break;
            case 10:
                abrirExtras();
                break;
            case 0:
                emExecucao = false;
                System.out.println("Ate a proxima!");
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private String descricaoDaVez() {
        Jogador dono = cidade.getDono();
        return dono.getNome() + " (" + dono.getTitulo() + "), de " + cidade.getNome();
    }

    /** Pergunta de 1 a 4 jogadores (inválido vira 2) e cadastra jogador e cidade de cada um. */
    private void iniciarNovoJogo() {
        System.out.print("Quantos jogadores (1 a 4)? ");
        int quantidade = lerQuantidade();
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            quantidade = 2;
        }

        Partida nova = new Partida();
        for (int i = 1; i <= quantidade; i++) {
            System.out.print("Nome do jogador " + i + ": ");
            String nome = lerTexto();
            if (nome.isEmpty()) {
                nome = "Jogador " + i;
            }
            String padrao = CIDADES_PADRAO[i - 1];
            System.out.print("Nome da cidade de " + nome + " (Enter = " + padrao + "): ");
            String nomeCidade = lerTexto();
            if (nomeCidade.isEmpty()) {
                nomeCidade = padrao;
            }
            nova.adicionarCidade(new Cidade(nomeCidade, new Jogador(nome)));
        }

        partida = nova;
        cidade = partida.getCidadeAtual();
        System.out.println();
        System.out.println("Novo jogo iniciado! Ano " + partida.getAnoAtual()
                + ". A partida vai ate o fim de " + partida.getAnoFinal()
                + " ou ate alguem virar Rei/Rainha.");
        System.out.println("Comeca: " + descricaoDaVez() + ".");
    }

    private void exibirStatus() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        cidade.exibirStatus();
    }

    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noMercado = true;
        while (noMercado) {
            System.out.println();
            System.out.println("--- MERCADO ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras());
            System.out.println("Preco do grao: " + mercado.getPrecoGrao()
                    + " florins | Preco da terra: " + mercado.getPrecoTerra() + " florim");
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void comprarGraos() {
        System.out.print("Quantidade de graos a comprar: ");
        int quantidade = lerQuantidade();
        if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("Compra realizada. Florins: " + cidade.getFlorins()
                    + " | Graos: " + cidade.getGraos());
        } else {
            System.out.println("Operacao recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderGraos() {
        System.out.print("Quantidade de graos a vender: ");
        int quantidade = lerQuantidade();
        if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("Venda realizada. Florins: " + cidade.getFlorins()
                    + " | Graos: " + cidade.getGraos());
        } else {
            System.out.println("Operacao recusada: quantidade invalida ou graos insuficientes.");
        }
    }

    private void comprarTerras() {
        System.out.print("Quantidade de hectares a comprar: ");
        int quantidade = lerQuantidade();
        if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("Compra realizada. Florins: " + cidade.getFlorins()
                    + " | Terras: " + cidade.getTerras());
        } else {
            System.out.println("Operacao recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderTerras() {
        System.out.print("Quantidade de hectares a vender: ");
        int quantidade = lerQuantidade();
        if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("Venda realizada. Florins: " + cidade.getFlorins()
                    + " | Terras: " + cidade.getTerras());
        } else {
            System.out.println("Operacao recusada: quantidade invalida ou terras insuficientes.");
        }
    }

    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean naAgricultura = true;
        while (naAgricultura) {
            System.out.println();
            System.out.println("--- AGRICULTURA ---");
            System.out.println("Terras: " + cidade.getTerras()
                    + " | Servos: " + cidade.getServos()
                    + " | Graos: " + cidade.getGraos());
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);
        if (hectares < 0) {
            System.out.println("A colheita deste ano ja foi feita. Tente de novo no proximo ano.");
            return;
        }
        System.out.println("Colheita realizada em " + hectares + " hectares.");
        System.out.println("Graos em estoque: " + cidade.getGraos());
    }

    private void distribuirAlimentos() {
        System.out.print("Percentual de graos a distribuir (20 a 80): ");
        int percentual = lerQuantidade();
        int servosAntes = cidade.getServos();
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos. Servos: " + servosAntes
                    + " -> " + cidade.getServos());
            System.out.println("Graos em estoque: " + cidade.getGraos());
        } else {
            System.out.println("Percentual invalido: use um valor de 20 a 80.");
        }
    }

    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println();
            System.out.println("--- GOVERNO ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Taxa de imposto: " + cidade.getTaxaImposto() + "%"
                    + " | Justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        if (arrecadado < 0) {
            System.out.println("Os impostos deste ano ja foram arrecadados. Tente de novo no proximo ano.");
            return;
        }
        System.out.println("Impostos arrecadados: " + arrecadado + " florins.");
        System.out.println("Florins em caixa: " + cidade.getFlorins());
    }

    private void definirTaxaImposto() {
        System.out.print("Nova taxa de imposto (0 a 50): ");
        int taxa = lerQuantidade();
        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto definida para " + taxa + "%.");
        } else {
            System.out.println("Taxa invalida: use um valor de 0 a 50.");
        }
    }

    private void definirJustica() {
        System.out.println("1 - Justa");
        System.out.println("2 - Moderada");
        System.out.println("3 - Severa");
        System.out.print("Escolha o nivel de justica: ");
        int opcao = lerOpcao();
        String nivel;
        switch (opcao) {
            case 1:
                nivel = "Justa";
                break;
            case 2:
                nivel = "Moderada";
                break;
            case 3:
                nivel = "Severa";
                break;
            default:
                nivel = "";
        }
        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido para " + nivel + ".");
        } else {
            System.out.println("Opcao invalida. O nivel de justica nao foi alterado.");
        }
    }

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean naConstrucoes = true;
        while (naConstrucoes) {
            System.out.println();
            System.out.println("--- CONSTRUCOES ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Servos: " + cidade.getServos()
                    + " | Soldados: " + cidade.getSoldados()
                    + " | Construcoes: " + cidade.getConstrucoes());
            System.out.println("Soldado: " + construcoes.getPrecoSoldado()
                    + " florins + 1 servo | Construcao: " + construcoes.getPrecoConstrucao() + " florins");
            System.out.println("1 - Recrutar soldados");
            System.out.println("2 - Construir");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    naConstrucoes = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void recrutarSoldados() {
        System.out.print("Quantidade de soldados a recrutar: ");
        int quantidade = lerQuantidade();
        if (construcoes.recrutarSoldados(cidade, quantidade)) {
            System.out.println("Soldados recrutados. Soldados: " + cidade.getSoldados()
                    + " | Servos: " + cidade.getServos()
                    + " | Florins: " + cidade.getFlorins());
        } else {
            System.out.println("Operacao recusada: quantidade invalida, florins ou servos insuficientes.");
        }
    }

    private void construir() {
        System.out.print("Quantidade de construcoes: ");
        int quantidade = lerQuantidade();
        if (construcoes.construir(cidade, quantidade)) {
            System.out.println("Construcoes erguidas. Construcoes: " + cidade.getConstrucoes()
                    + " | Florins: " + cidade.getFlorins());
        } else {
            System.out.println("Operacao recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    /** Passa a vez; se o último jogador encerrou, fecha o ano (promoções, classificação, fim de jogo). */
    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        System.out.println("Turno de " + cidade.getDono().getNome() + " encerrado.");

        boolean fimDoAno = partida.passarTurno();
        if (fimDoAno) {
            int anoQueFechou = partida.getAnoAtual();
            System.out.println();
            System.out.println("========= FIM DO ANO " + anoQueFechou + " =========");
            if (eventosAtivos) {
                for (Cidade c : partida.getCidades()) {
                    String ocorrido = eventos.sortear(c);
                    if (ocorrido != null) {
                        System.out.println("[Evento] " + c.getNome() + ": " + ocorrido);
                    }
                }
            }
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            for (Cidade promovida : promovidas) {
                System.out.println(promovida.getDono().getNome() + ", de " + promovida.getNome()
                        + ", agora e " + promovida.getDono().getTitulo() + "!");
            }
            exibirClassificacao();

            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
            System.out.println();
            System.out.println("Comeca o ano " + partida.getAnoAtual() + ".");
        }

        cidade = partida.getCidadeAtual();
        System.out.println("Agora e a vez de " + descricaoDaVez() + ".");
    }

    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        System.out.println();
        System.out.println("----------- CLASSIFICACAO -----------");
        for (Cidade c : partida.getCidades()) {
            Jogador dono = c.getDono();
            System.out.println(c.getNome() + " | " + dono.getNome() + " (" + dono.getTitulo()
                    + ") | prestigio " + nobreza.calcularPrestigio(c));
            int proximo = nobreza.prestigioParaProximoTitulo(dono);
            if (proximo < 0) {
                System.out.println("        (ja e Rei/Rainha)");
            } else {
                System.out.println("        (proximo titulo com " + proximo + ")");
            }
        }
    }

    /** Anuncia o vencedor e encerra a partida (volta ao estado "sem jogo"). */
    private void encerrarPartida() {
        Cidade vencedora = partida.determinarVencedor(nobreza);
        Jogador vencedor = vencedora.getDono();
        System.out.println();
        System.out.println("========= FIM DE JOGO =========");
        if (vencedor.ehMonarca()) {
            System.out.println(vencedor.getNome() + " virou " + vencedor.getTitulo() + "!");
        } else {
            System.out.println("Chegamos ao fim de " + partida.getAnoFinal()
                    + ". Vence quem tem o titulo mais alto (empate: maior prestigio).");
        }
        System.out.println("Vencedor: " + vencedor.getNome() + " (" + vencedor.getTitulo() + "), de "
                + vencedora.getNome() + ", com prestigio " + nobreza.calcularPrestigio(vencedora) + ".");
        partida = null;
        cidade = null;
    }

    /** Extras (diferenciais do projeto): salvar/carregar, eventos aleatórios e conselheiro. */
    private void abrirExtras() {
        boolean nosExtras = true;
        while (nosExtras) {
            System.out.println();
            System.out.println("--- EXTRAS ---");
            System.out.println("1 - Salvar partida");
            System.out.println("2 - Carregar partida");
            System.out.println("3 - Eventos aleatorios no fim do ano: " + (eventosAtivos ? "LIGADOS" : "desligados") + " (alternar)");
            System.out.println("4 - Pedir conselho ao conselheiro");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    salvarPartida();
                    break;
                case 2:
                    carregarPartida();
                    break;
                case 3:
                    eventosAtivos = !eventosAtivos;
                    System.out.println("Eventos aleatorios " + (eventosAtivos ? "ligados." : "desligados."));
                    break;
                case 4:
                    pedirConselho();
                    break;
                case 0:
                    nosExtras = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void salvarPartida() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        try {
            Persistencia.salvar(partida, ARQUIVO_SAVE);
            System.out.println("Partida salva em " + ARQUIVO_SAVE + ".");
        } catch (IOException e) {
            System.out.println("Nao foi possivel salvar: " + e.getMessage());
        }
    }

    private void carregarPartida() {
        try {
            Partida carregada = Persistencia.carregar(ARQUIVO_SAVE);
            partida = carregada;
            cidade = partida.getCidadeAtual();
            System.out.println("Partida carregada. Ano " + partida.getAnoAtual() + ".");
            System.out.println("Agora e a vez de " + descricaoDaVez() + ".");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Nao foi possivel carregar: " + e.getMessage());
        }
    }

    private void pedirConselho() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        System.out.println();
        System.out.println("--- CONSELHEIRO ---");
        System.out.println(conselheiro.aconselhar(cidade, nobreza));
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval, a partir do ano 1400.");
        System.out.println("De 1 a 4 jogadores governam cidades-Estado no mesmo computador,");
        System.out.println("um turno de cada vez. Colher e arrecadar impostos valem uma vez por ano.");
        System.out.println("No fim do ano, quem tem prestigio suficiente sobe um titulo:");
        System.out.println("Cavaleiro, Barao, Conde, Marques, Duque, Rei/Rainha.");
        System.out.println("Prestigio = construcoes x 10 + soldados x 2 + servos + terras / 100.");
        System.out.println("Vence quem virar Rei/Rainha primeiro ou, ao fim de 1410, o titulo mais alto.");
    }
}

package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo (Etapas 1 a 4).
 * Controla o laço principal e delega o trabalho às classes certas;
 * as regras do jogo moram nas classes de serviço, não aqui.
 */
public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private boolean emExecucao = true;
    private Jogador jogador;
    private Cidade cidade;
    private final Mercado mercado = new Mercado();
    private final Agricultura agricultura = new Agricultura();

    /** Laço principal: repete até o jogador escolher sair. */
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    /** Apenas imprime o menu; não lê nada do teclado. */
    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO");
        System.out.println(" Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Lê uma linha e converte para inteiro; devolve -1 se for inválida. */
    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo / volta do submenu
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Lê e valida um número digitado; devolve -1 se não for um inteiro válido. */
    private int lerQuantidade() {
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
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                System.out.println("Ate a proxima!");
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    /** Avisa quando ainda não existe partida em andamento. */
    private boolean partidaIniciada() {
        if (cidade == null) {
            System.out.println("Nenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    /** Cria o Jogador e a Cidade perguntando os nomes (padrões: Jogador 1 / Bolonha). */
    private void iniciarNovoJogo() {
        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = lerTexto();
        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = lerTexto();

        if (nomeJogador.isEmpty()) {
            nomeJogador = "Jogador 1";
        }
        if (nomeCidade.isEmpty()) {
            nomeCidade = "Bolonha";
        }

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (partidaIniciada()) {
            cidade.exibirStatus();
        }
    }

    private void abrirMercado() {
        if (!partidaIniciada()) {
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
        if (!partidaIniciada()) {
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

    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval, a partir do ano 1400.");
        System.out.println("Cada jogador governa uma cidade-Estado e decide como usar");
        System.out.println("florins, graos, terras e soldados para desenvolver o territorio");
        System.out.println("e conquistar titulos de nobreza, ate chegar a rei ou rainha.");
    }
}

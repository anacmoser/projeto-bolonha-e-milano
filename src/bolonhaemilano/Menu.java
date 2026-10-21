package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo (Etapas 1 + 2).
 * Opções: 1 - Novo jogo, 2 - Ver status da cidade, 3 - Sobre o jogo, 0 - Sair.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
    }

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
        System.out.println("3 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Lê uma linha e converte para inteiro; devolve -1 se for inválida. */
    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                if (cidade == null) {
                    System.out.println("Nenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
                } else {
                    cidade.exibirStatus();
                }
                break;
            case 3:
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

    /** Cria o Jogador e a Cidade perguntando os nomes. */
    private void iniciarNovoJogo() {
        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = scanner.nextLine().trim();
        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = scanner.nextLine().trim();

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        cidade.exibirStatus();
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

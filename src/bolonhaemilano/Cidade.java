package bolonhaemilano;

/**
 * Representa a cidade governada por um jogador. O jogo começa no ano 1400.
 */
public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
    }

    /** Incrementa o ano em 1 (só será chamado a partir da Etapa 7). */
    public void avancarAno() {
        anoAtual++;
    }

    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
    }
}

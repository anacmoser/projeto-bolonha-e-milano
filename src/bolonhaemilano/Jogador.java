package bolonhaemilano;

/**
 * Representa o jogador (governante). Começa com o título "Cavaleiro".
 */
public class Jogador {

    private String nome;
    private String titulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.titulo = "Cavaleiro";
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }
}

package bolonhaemilano;

/**
 * Representa o jogador (governante). O título agora é um nível (0 a 5) que
 * só sobe um degrau por vez, com promover(): ninguém escolhe o título de fora.
 */
public class Jogador {

    private static final String[] TITULOS = {"Cavaleiro", "Barao", "Conde", "Marques", "Duque", "Rei/Rainha"};

    private final String nome;
    private int nivelTitulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.nivelTitulo = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return TITULOS[nivelTitulo];
    }

    public int getNivelTitulo() {
        return nivelTitulo;
    }

    /** Sobe um título. Devolve false se já for Rei/Rainha. */
    public boolean promover() {
        if (ehMonarca()) {
            return false;
        }
        nivelTitulo++;
        return true;
    }

    /** true se já chegou ao último título (Rei/Rainha). */
    public boolean ehMonarca() {
        return nivelTitulo == TITULOS.length - 1;
    }
}

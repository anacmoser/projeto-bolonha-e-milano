package bolonhaemilano;

/**
 * Representa a cidade governada por um jogador: guarda os recursos e as
 * configurações da partida. Nenhum recurso pode ficar negativo.
 */
public class Cidade {

    private static final int ANO_INICIAL = 1400;
    private static final int FLORINS_INICIAIS = 1000;
    private static final int TERRAS_INICIAIS = 10000;
    private static final int GRAOS_INICIAIS = 500;

    private final String nome;
    private final Jogador dono;
    private int anoAtual;
    private int florins;
    private int terras;
    private int graos;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = ANO_INICIAL;
        this.florins = FLORINS_INICIAIS;
        this.terras = TERRAS_INICIAIS;
        this.graos = GRAOS_INICIAIS;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }

    public int getFlorins() {
        return florins;
    }

    public int getTerras() {
        return terras;
    }

    public int getGraos() {
        return graos;
    }

    /** Incrementa o ano em 1 (só será chamado a partir da Etapa 7). */
    public void avancarAno() {
        anoAtual++;
    }

    /** Adiciona florins; quantidades não positivas são ignoradas. */
    public void adicionarFlorins(int quantidade) {
        if (quantidade > 0) {
            florins += quantidade;
        }
    }

    /** Remove florins; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerFlorins(int quantidade) {
        if (quantidade < 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    /** Adiciona terras; quantidades não positivas são ignoradas. */
    public void adicionarTerras(int quantidade) {
        if (quantidade > 0) {
            terras += quantidade;
        }
    }

    /** Remove terras; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerTerras(int quantidade) {
        if (quantidade < 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    /** Adiciona graos; quantidades não positivas são ignoradas. */
    public void adicionarGraos(int quantidade) {
        if (quantidade > 0) {
            graos += quantidade;
        }
    }

    /** Remove graos; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerGraos(int quantidade) {
        if (quantidade < 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos: " + graos);
    }
}

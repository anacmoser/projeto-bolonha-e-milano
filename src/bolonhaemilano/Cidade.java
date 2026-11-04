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
    private static final int SERVOS_INICIAIS = 200;
    private static final int TAXA_IMPOSTO_INICIAL = 10;
    private static final String NIVEL_JUSTICA_INICIAL = "Moderada";
    private static final int SOLDADOS_INICIAIS = 0;
    private static final int CONSTRUCOES_INICIAIS = 0;

    private final String nome;
    private final Jogador dono;
    private int anoAtual;
    private int florins;
    private int terras;
    private int graos;
    private int servos;
    private int taxaImposto;
    private String nivelJustica;
    private int soldados;
    private int construcoes;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = ANO_INICIAL;
        this.florins = FLORINS_INICIAIS;
        this.terras = TERRAS_INICIAIS;
        this.graos = GRAOS_INICIAIS;
        this.servos = SERVOS_INICIAIS;
        this.taxaImposto = TAXA_IMPOSTO_INICIAL;
        this.nivelJustica = NIVEL_JUSTICA_INICIAL;
        this.soldados = SOLDADOS_INICIAIS;
        this.construcoes = CONSTRUCOES_INICIAIS;
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

    public int getServos() {
        return servos;
    }

    public int getTaxaImposto() {
        return taxaImposto;
    }

    public void setTaxaImposto(int taxa) {
        this.taxaImposto = taxa;
    }

    public String getNivelJustica() {
        return nivelJustica;
    }

    public void setNivelJustica(String nivel) {
        this.nivelJustica = nivel;
    }

    public int getSoldados() {
        return soldados;
    }

    public int getConstrucoes() {
        return construcoes;
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

    /** Adiciona servos; quantidades não positivas são ignoradas. */
    public void adicionarServos(int quantidade) {
        if (quantidade > 0) {
            servos += quantidade;
        }
    }

    /** Remove servos; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerServos(int quantidade) {
        if (quantidade < 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    /** Adiciona soldados; quantidades não positivas são ignoradas. */
    public void adicionarSoldados(int quantidade) {
        if (quantidade > 0) {
            soldados += quantidade;
        }
    }

    /** Remove soldados; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerSoldados(int quantidade) {
        if (quantidade < 0 || quantidade > soldados) {
            return false;
        }
        soldados -= quantidade;
        return true;
    }

    /** Adiciona construcoes; quantidades não positivas são ignoradas. */
    public void adicionarConstrucoes(int quantidade) {
        if (quantidade > 0) {
            construcoes += quantidade;
        }
    }

    /** Remove construcoes; devolve false (sem alterar nada) se não houver quantidade suficiente. */
    public boolean removerConstrucoes(int quantidade) {
        if (quantidade < 0 || quantidade > construcoes) {
            return false;
        }
        construcoes -= quantidade;
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
        System.out.println("Servos: " + servos);
        System.out.println("Taxa de imposto: " + taxaImposto + "%");
        System.out.println("Nivel de justica: " + nivelJustica);
        System.out.println("Soldados: " + soldados);
        System.out.println("Construcoes: " + construcoes);
    }
}

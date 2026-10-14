package bolonhaemilano;

/**
 * Ponto de entrada do jogo Bolonha e Milano.
 * A Main apenas cria o Menu e delega a execução a ele.
 */
public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.executar();
    }
}

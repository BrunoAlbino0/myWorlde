import java.util.Scanner;

public class Wordle {
    private Game game;
    private int game_max_atempts = 6;
    private Boolean in_execution;
    private Scanner inputScanner;

    private Game_Statistics statistics;
    public void Start(){
        statistics = new Game_Statistics();

        this.game = new Game(game_max_atempts, statistics);
        in_execution = true;
        inputScanner = new Scanner(System.in);

        Show_menu();

        while (in_execution){
            int user_input = inputScanner.nextInt();

            switch (user_input){
                case 1:
                    game.Start();
                    Show_menu();
                    break;
                case 2:
                    System.out.println(statistics.toString());
                    Show_menu();
                    break;
                case 3 :
                    in_execution = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.print("\n> ");
            }
        }
        System.out.println("Bye hope to see you soon!\nShuting down...");
    }

    private void Show_menu(){
        System.out.println("\n\n                Worlde82               \n");
        System.out.println("1. Start a new game");
        System.out.println("2. Statics");
        System.out.println("3. Exit");
        System.out.print(">");
    }

}

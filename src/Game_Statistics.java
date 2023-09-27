import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Game_Statistics {
    private final String statistics_file_name = new String("src/statistics.txt");

    private int games_played;
    private int games_won;
    private int best_try;
    private int current_streak;
    private int max_streak;
    private int best_Try1;
    private int best_Try2;
    private int best_Try3;
    private int best_Try4;
    private int best_Try5;
    private int best_Try6;


    Game_Statistics(){
        games_played = 0;
        games_won = 0;
        best_try = 0;
        current_streak = 0;
        best_Try1 = 0;
        best_Try2 = 0;
        best_Try3 = 0;
        best_Try4 = 0;
        best_Try5 = 0;
        best_Try6 = 0;

        Load_Statistics();
    }

    public void Update_Statistics(boolean result,int attempt_number) {

        if (result){
            games_played++;
            games_won++;
            current_streak ++;
            switch (attempt_number){
                case 1:
                    best_Try1++;
                    break;
                case 2:
                    best_Try2++;
                    break;
                case 3:
                    best_Try3++;
                    break;
                case 4:
                    best_Try4++;
                    break;
                case 5:
                    best_Try5++;
                    break;
                case 6:
                    best_Try6++;
                    break;
            }

            if (current_streak > max_streak){
                max_streak = current_streak;
            }

            if (best_try == 0 || best_try > attempt_number ){
                best_try = attempt_number;
            }
        }else{
            games_played++;
            current_streak = 0;
        }

        Save_Statistics();
    }

    public void Load_Statistics(){
        File file;
        //System.out.println("Loading Statistics...");
        try{
            file= new File(statistics_file_name);
            Scanner myReader = new Scanner(file);
            int line = 1;

            while (myReader.hasNextLine()){
                String data = myReader.nextLine();

                String aux;
                if(data.length() > 0) {
                    aux = data.split(":")[1].strip();
                    int value_read = Integer.parseInt(aux);

                switch (line){
                    case 1: //Games Played
                        games_played = value_read;
                        break;
                    case 2: //Games Won
                        games_won = value_read;
                        break;
                    case 3: //Best Try
                        best_try = value_read;
                        break;
                    case 4: //Current Streak
                        current_streak = value_read;
                        break;
                    case 5: //Max Streak
                        max_streak = value_read;
                        break;
                    case 6: //Best Try 1
                        best_Try1 = value_read;
                        break;
                    case 7: //Best Try 2
                        best_Try2 = value_read;
                        break;
                    case 8: //Best Try 3
                        best_Try3 = value_read;
                        break;
                    case 9: //Best Try 4
                        best_Try4 = value_read;
                        break;
                    case 10: //Best Try 5
                        best_Try5 = value_read;
                        break;
                    case 11: //Bes Try 6
                        best_Try6 = value_read;
                        break;
                }

                }
                line++;


            }
            myReader.close();
        }catch (FileNotFoundException e){
            System.out.println("Statistics File not found");
        }
    }


    public void Save_Statistics(){
        File file = new File(statistics_file_name);

        try {
            FileWriter myWriter = new FileWriter(file);

            myWriter.write("Games Played: " + games_played + "\n");
            myWriter.write("Games Won: " + games_won + "\n");
            myWriter.write("Best Try: " + best_try + "\n");
            myWriter.write("Current Streak: " + current_streak + "\n");
            myWriter.write("Max Streak: " + max_streak + "\n");
            myWriter.write("Best Try#1: " + best_Try1 + "\n");
            myWriter.write("Best Try#2: " + best_Try2 + "\n");
            myWriter.write("Best Try#3: " + best_Try3 + "\n");
            myWriter.write("Best Try#4: " + best_Try4 + "\n");
            myWriter.write("Best Try#5: " + best_Try5 + "\n");
            myWriter.write("Best Try#6: " + best_Try6 + "\n");

            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String newString = new String();
        newString = "\n\n                Statistics               \n";
        newString += "Games Played: " + games_played +"\n";
        newString += "Games Won: " + games_won + "\n";
        float percentage_games_won = games_played > 0 ? ((float)games_won/games_played ) * 100 : 0;
        newString += "Percentage of games Won: " + String.format("%.2f",percentage_games_won) + "%\n";
        newString += "Current Streak: " + current_streak + "\n";
        newString += "Max Streak: " + max_streak + "\n";
        float percentage_try_1 = games_won > 0 ? ((float) best_Try1/games_won) * 100 : 0;
        float percentage_try_2 = games_won > 0 ? ((float) best_Try2/games_won) * 100 : 0;
        float percentage_try_3 = games_won > 0 ? ((float) best_Try3/games_won) * 100 : 0;
        float percentage_try_4 = games_won > 0 ? ((float) best_Try4/games_won) * 100 : 0;
        float percentage_try_5 = games_won > 0 ? ((float) best_Try5/games_won) * 100 : 0;
        float percentage_try_6 = games_won > 0 ? ((float) best_Try6/games_won) * 100 : 0;
        newString +="   Best attempts destribution: \n";
        newString += "#1:\t"+ String.format("%.2f",percentage_try_1) + "%\tTotal:"+best_Try1+"\n";
        newString += "#2:\t"+ String.format("%.2f",percentage_try_2) + "%\tTotal:"+best_Try2+"\n";
        newString += "#3:\t"+ String.format("%.2f",percentage_try_3) + "%\tTotal:"+best_Try3+"\n";
        newString += "#4:\t"+ String.format("%.2f",percentage_try_4) + "%\tTotal:"+best_Try4+"\n";
        newString += "#5:\t"+ String.format("%.2f",percentage_try_5) + "%\tTotal:"+best_Try5+"\n";
        newString += "#6:\t"+ String.format("%.2f",percentage_try_6) + "%\tTotal:"+best_Try6+"\n";

        return  newString;
    }
}

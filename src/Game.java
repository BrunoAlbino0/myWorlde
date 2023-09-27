import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;


public class Game {

    private String word_to_guess;
    private boolean in_game;
    private final int max_attempts;
    private int current_attempt;
    private ArrayList<ArrayList<Letter_Details>> user_guesses;

    private final int max_word_length = 5;

    private ArrayList<String> dictionary = new ArrayList<>();

    //Valor 0 = Não é usado, 1-Posição incorreta,2 - Usado e posição certa;
    private HashMap<Character, Integer> chars_used = new HashMap<Character, Integer>();
    private Scanner game_scanner;

    private Game_Statistics statistics;


    public Game(int max_attempts, Game_Statistics statistics){
       // Load_Hardcoded_Dictionary();
        Load_Dictionary_From_File();
        this.max_attempts = max_attempts;
        game_scanner = new Scanner(System.in);
        user_guesses = new ArrayList<ArrayList<Letter_Details>>();
        chars_used = new HashMap<>();
        this.statistics = statistics;
    }

    public void Start(){

        if ( dictionary.isEmpty()){
            System.out.println("ERROR - Impossible to continue dictionary wasn't properly loaded");
            return;
        }

        Game_Setup();

        while(in_game){
            System.out.println("Attempt " + current_attempt);
            System.out.print(">");
            String user_input = game_scanner.nextLine();

            if ( Validade_Inputed_Word(user_input)){
                user_input = Clean_Word(user_input);

                System.out.println("Inserted: " + user_input);
                if (Game_Validation(user_input)){
                    statistics.Update_Statistics(true, current_attempt);
                    System.out.println("Congratulations, you won!");
                    in_game = false;
                }else {
                    if (current_attempt >= max_attempts) {
                        statistics.Update_Statistics(false, current_attempt);
                        in_game = false;
                        System.out.println("Maximum number of attempts reached, you have lost!");
                        System.out.println("The correct word was: " + word_to_guess);
                    }else {
                        System.out.println("Try Again!\n");
                        Show_Used_Characters_Detail();
                    }
                }
                current_attempt++;
            }else{
                System.out.println("The word inserted was invalid, try again!\n");
            }

        }
        statistics.Save_Statistics();
        System.out.println("Redirecting to the menu...");
    }

    private String Initiate_word(){

        //1.0 Palavra Fixa
        //return new String("BOLHA");

        Random rand = new Random(System.currentTimeMillis());
        if (dictionary.size() < 1){
            return "";
        }
        return dictionary.get(rand.nextInt(dictionary.size()));
    }

    private boolean Validade_Inputed_Word(String user_input){
        user_input = user_input.toUpperCase();

        //Tem que ter 5 caracteres
        if (user_input.length() != max_word_length){
            return false;
        }

        //não pode conter numeros
        //Não pode conter espaços em branco
        for (char c : user_input.toCharArray()){
            if (Character.isDigit(c) || c == ' ' || !Character.isLetter(c)){
                return false;
            }
        }
        user_input = Clean_Word(user_input);

        //Verificar se pertence ao dicionario
        if (dictionary.contains(user_input)){
            return true;
        }else {
            System.out.println(user_input +" isn't on dictionary");
            return false;
        }

       // return true;
    }

    private Boolean Game_Validation(String user_input) {

        if (word_to_guess.equals(user_input.toUpperCase())) {
            return true;
        }
        char[] word_to_guess_chars = word_to_guess.toCharArray();
        char[] user_input_chars = user_input.toUpperCase().toCharArray();

        //Fill_user_guesses(user_input_chars);

        ArrayList<Letter_Details> array_letter_details = new ArrayList<Letter_Details>();

        for (int i = 0; i < user_input_chars.length; i++) {
            System.out.print("\nCharacter: '" + user_input_chars[i] + "'");
            if (user_input_chars[i] == word_to_guess_chars[i]) {
                System.out.print(" Correct");
                Handle_Used_Chars(user_input_chars[i], 2);
                array_letter_details.add(new Letter_Details(user_input_chars[i], Letter_Details.Status_Types.RIGHT));
                continue;
            } else {
                boolean char_in_word = false;
                for (int j = 0; j < word_to_guess_chars.length; j++) {
                    if (user_input_chars[i] == word_to_guess_chars[j]) {
                        System.out.print(" Wrong Position");
                        char_in_word = true;
                        break;
                    }
                }
                if (char_in_word) {
                    Handle_Used_Chars(user_input_chars[i], 1);
                    array_letter_details.add(new Letter_Details(user_input_chars[i], Letter_Details.Status_Types.EXCHANGED));
                    continue;
                    }
                }
                Handle_Used_Chars(user_input_chars[i], 0);
                array_letter_details.add(new Letter_Details(user_input_chars[i], Letter_Details.Status_Types.UNUSED));
                System.out.print(" Incorrect");
            }
            user_guesses.add(array_letter_details);
            System.out.print("\n\n");
            return false;
    }

    private void Load_Hardcoded_Dictionary(){
        dictionary.add("BOLHA");
        dictionary.add("NOBRE");
        dictionary.add("FAZER");
        dictionary.add("IDEIA");
        dictionary.add("MORAL");
        dictionary.add("PODER");
        dictionary.add("JUSTO");
        dictionary.add("ANEXO");
        dictionary.add("ANEXO");
        dictionary.add("TEMPO");
        dictionary.add("PESAR");
        dictionary.add("DIZER");
        dictionary.add("CULTO");
        dictionary.add("FORTE");
        dictionary.add("FELIZ");
        dictionary.add("TENRO");
        dictionary.add("MANSO");
        dictionary.add("LUGAR");
    }

    private void Load_Dictionary_From_File(){
       // System.out.println("Loading Dictionary...");
        try{
            File myFile = new File("src/palavras.txt");
            Scanner myReader = new Scanner(myFile);

            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                if (data.length() == max_word_length) {
                    dictionary.add(Clean_Word(data.toUpperCase()));
                   // System.out.println("Adicionado ao dicionario: " + data);
                }
            }
            myReader.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            in_game = false;
        }
    }

    private void Game_Setup(){
        current_attempt = 1;
        System.out.println("Starting a new game\n");

        word_to_guess = Initiate_word();
        //System.out.println("Selected Word: " +word_to_guess );

        in_game = true;

       if (!chars_used.isEmpty()){
           chars_used.clear();
       }

        if (!user_guesses.isEmpty()){
            user_guesses.clear();
        }
    }

    private void Show_Used_Characters_Detail() {

        if (!user_guesses.isEmpty()){
            System.out.println("Previous Guesses:");
            System.out.println(Show_User_Guesses());
        }

        System.out.println("\nUsed characters:");
        ArrayList<Character> right_characters = new ArrayList<>();
        ArrayList<Character> misplaced_characters = new ArrayList<>();
        ArrayList<Character> wrong_characters = new ArrayList<>();

        for(Character c : chars_used.keySet()){

            switch (chars_used.get(c)){
                case 0:
                    wrong_characters.add(c);
                    break;
                case 1:
                    misplaced_characters.add(c);
                    break;
                case 2:
                    right_characters.add(c);
                    break;
                default:
            }
        }

        System.out.println("Right Position: " + right_characters);
        System.out.println("Wrong Position: " + misplaced_characters);
        System.out.println("Not in the Word: " + wrong_characters);
    }

    private String Show_User_Guesses(){
        String to_return = "";
        if(!user_guesses.isEmpty()){
            for (ArrayList<Letter_Details> guess : user_guesses){
                to_return += guess.toString() + "\n";
            }
        }
        return to_return;
    }

    private void Handle_Used_Chars(char c, int value){
        if (value == 2){
            chars_used.put(c, value);
        }else{
            Integer aux = chars_used.get(c);
            if (aux != null){
                if (aux < value){
                    chars_used.put(c, value);
                }
            }else{
                chars_used.put(c, value);
            }
        }
    }

    private String Clean_Word(String word ){

        String clean_Word = "";

        for (char c : word.toCharArray()){
            c = Remove_Accents(c);
            clean_Word += c;
        }
        return clean_Word;
    }
    private char Remove_Accents(char c){

        switch (c){
            case 'Á','À','Ã','Â':
                c = 'A';
                break;
            case 'Í','Ì','Î':
                c = 'I';
                break;
            case 'É','È','Ê':
                c = 'E';
                break;
            case 'Ó','Ò','Ô','Õ':
                c = 'O';
                break;
            case 'Ú','Ù','Û':
                c = 'U';
                break;
        }

        return c;
    }

}

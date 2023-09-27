public class Letter_Details {
    public enum Status_Types{
        UNUSED,
        EXCHANGED,
        RIGHT
    }

    private final String RESET = "\u001B[0m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";

    private Status_Types status;
    private char letter;


    Letter_Details(char letter, Status_Types status){
        this.letter = letter;
        this.status = status;
    }

    @Override
    public String toString() {
        String to_return = new String();
        switch (status){
            case EXCHANGED:
                to_return = YELLOW ;
                break;
            case RIGHT:
                to_return = GREEN ;
                break;
            default:
                to_return = RESET;
        }
        to_return = to_return + letter + RESET;
        return to_return;
    }

}

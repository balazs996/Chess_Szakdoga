package pepe.hands.chess;

public class ChessConverter {
    public static int codeToPosition (String input){
        int output = 0;
        output = (8-Integer.parseInt(input.substring(1))) * 8 + (((int)input.charAt(0)) - 97);
        return output;
    }
}

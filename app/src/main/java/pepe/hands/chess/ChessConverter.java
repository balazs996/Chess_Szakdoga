package pepe.hands.chess;

import java.util.ArrayList;

public class ChessConverter {
    public static int codeToPosition(String input) {
        return (8 - Integer.parseInt(input.substring(1))) * 8 + (((int) input.charAt(0)) - 97);
    }

    public static String positionToCode(int input) {
        char letter = (char) (97 + (input % 8));
        int szam = 8 - input / 8;
        return ("" + letter + szam);
    }

    public static ArrayList<ChessPiece> putDownPiece(ArrayList<ChessPiece> table, ChessPiece piece, String... Positions) {
        for (String position : Positions        ) {
            table.set(codeToPosition(position),piece);
        }
        return table;
    }
}

package pepe.hands.chess;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessUtility<moves> {

    public enum gameStatus{
        NORMAL, CHECK, STALEMATE, CHECKMATE

    }

    private static final String TAG = "ChessUtility";
    public static int codeToPosition(String input) {
        return (8 - Integer.parseInt(input.substring(1))) * 8 + (((int) input.charAt(0)) - 97);
    }

    public static int rowCC(int row, int column){
        return ((8 - row) * 8 + column-1);
    }

    public static String positionToCode(int input) {
        char letter = (char) (97 + (input % 8));
        int szam = 8 - input / 8;
        return ("" + letter + szam);
    }

    public static ArrayList<ChessPiece> putDownPiece(ArrayList<ChessPiece> table, ChessPiece.ChessType type, String... Positions) {
        for(ChessPiece.ChessColor color : ChessPiece.ChessColor.values()) {
            String row = color == ChessPiece.ChessColor.WHITE ? "1" : "8";
            if(type == ChessPiece.ChessType.PAWN)
                row = color == ChessPiece.ChessColor.WHITE ? "2" : "7";
            for (String position : Positions) {
                table.set(codeToPosition(position+row), new ChessPiece(color, type));
            }
        }
        return table;
    }

    public static ArrayList<String> calculateMoves(ChessPiece chessPiece, String position, ArrayList<ChessPiece> board, boolean onlyAttackMoves) {
        ArrayList<String> moves = new ArrayList<>();
        switch (chessPiece.getType()) {
            case PAWN: {
                moves = calculatePawn(position, board, chessPiece, onlyAttackMoves);
                break;
            }
            case ROOK: {
                moves = calculateRook(position, board);
                break;
            }
            case KNIGHT: {
                moves = calculateKnight(position, board);
                break;
            }
            case BISHOP: {
                moves = calculateBishop(position, board);
                break;
            }
            case QUEEN: {
                moves = calculateBishop(position, board);
                moves.addAll(calculateRook(position, board));
                break;
            }
            case KING: {
                moves = calculateKing(position, board);
                break;
            }
        }

        return moves;
    }

    //Bábuk lépései

    private static ArrayList<String> calculateBishop(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> bishopMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        ArrayList<String> operators = new ArrayList<>(Arrays.asList("+ +", "- -", "- +", "+ -"));

        Log.d(TAG, "tempPos: row: "+row + "__col: " + column);
        for(String op: operators) {
            boolean diagonal = true;
            String[] ops = op.split(" ");
            for(int i = 1; diagonal; i++) {
                int rowX = ops[0].equals("+") ? row + i : row - i;
                int columnX = ops[1].equals("+") ? column + i : column - i;
                if (columnX <= 8 && columnX >= 1 && rowX <= 8 && rowX >= 1) {
                    int tempPosition = rowCC(rowX, columnX);
                    if (board.get(tempPosition).getColor() == null || board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()) {
                        bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                        Log.d(TAG, "calculateBishop: " + ChessUtility.positionToCode(tempPosition));
                    }
                    if (board.get(tempPosition).getColor() != null) diagonal = false;
                }
                else{
                    diagonal = false;
                }
            }
        }

        return bishopMoves;
    }
    
    private static ArrayList<String> calculateRook(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> rookMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        ArrayList<Integer> multiply_values = new ArrayList<>(Arrays.asList(0, -1, 0, 1, -1, 0, 1, 0));

        for(int i = 0; i < multiply_values.size(); i+=2) {
            boolean horizontal = true;
            for (int j = 1; horizontal; j++) {
                int rowX = row + (j* multiply_values.get(i));
                int columnX = column + (j* multiply_values.get(i + 1));
                if (columnX <= 8 && columnX >= 1 && rowX <= 8 && rowX >= 1) {
                    int tempPosition = rowCC(rowX, columnX);
                    if (board.get(tempPosition).getColor() == null || board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()) {
                        rookMoves.add(ChessUtility.positionToCode(tempPosition));
                        Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                    }
                    if(board.get(tempPosition).getColor() != null) horizontal = false;
                }
                else{
                    horizontal = false;
                }

            }
        }

        return rookMoves;
    }

    private static ArrayList<String> calculatePawn(String position, ArrayList<ChessPiece> board, ChessPiece chessPiece, boolean onlyAttacked) {
        ArrayList<String> pawnMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));

        int rowModifier = chessPiece.getColor() == ChessPiece.ChessColor.WHITE ? 1 : -1;
        int tempPosition = rowCC(row + rowModifier, column);
        if (onlyAttacked) {
            pawnMoves.add(ChessUtility.positionToCode(rowCC(row + rowModifier, column - 1)));
            pawnMoves.add(ChessUtility.positionToCode(rowCC(row + rowModifier, column + 1)));
        } else {
            if (board.get(tempPosition).getColor() == null) {
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
                if (!chessPiece.isMoved()) {
                    tempPosition = rowCC(row + (rowModifier * 2), column);
                    if (board.get(tempPosition).getColor() == null)
                        pawnMoves.add(ChessUtility.positionToCode(tempPosition));
                }
            }

            tempPosition = rowCC(row + rowModifier, column - 1);
            if (column >= 1 && board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != chessPiece.getColor()) {
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
            tempPosition = rowCC(row + rowModifier, column + 1);
            if (column <= 8 && board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != chessPiece.getColor()) {
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }


            //TODO: En-passant move
        }

        return pawnMoves;
    }

    private static ArrayList<String> calculateKing(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> kingMoves = new ArrayList<>();
        ArrayList<String> checkMoves = attackedFields(position, board);
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        ArrayList<Integer> multiply_values = new ArrayList<>(Arrays.asList(0, -1, 0, 1, -1, 0, 1, 0, 1, 1, -1, -1, -1, 1, 1, -1));
        for (int i = 0; i < multiply_values.size(); i += 2) {
            int rowX = row + (multiply_values.get(i));
            int columnX = column + (multiply_values.get(i + 1));
            if (columnX <= 8 && columnX >= 1 && rowX <= 8 && rowX >= 1) {
                int tempPosition = rowCC(rowX, columnX);
                if (board.get(tempPosition).getColor() == null || board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()) {
                    kingMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateKing: " + ChessUtility.positionToCode(tempPosition));
                }
            }
        }

        kingMoves.removeAll(checkMoves);
        return kingMoves;
    }

    private static ArrayList<String> calculateKnight(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> knightMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        ArrayList<Integer> multiply_values = new ArrayList<>(Arrays.asList(2, 1, 2, -1, -2, 1, -2, -1, 1, 2, 1, -2, -1, 2, -1, -2));
        for (int i = 0; i < multiply_values.size(); i += 2) {
            int rowX = row + (multiply_values.get(i));
            int columnX = column + (multiply_values.get(i + 1));
            if (columnX <= 8 && columnX >= 1 && rowX <= 8 && rowX >= 1) {
                int tempPosition = rowCC(rowX, columnX);
                if (board.get(tempPosition).getColor() == null || board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()) {
                    knightMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateKing: " + ChessUtility.positionToCode(tempPosition));
                }
            }
        }
        return knightMoves;
    }

    //Sakk-Matt

    private static ArrayList<String> attackedFields(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> attackedFields = new ArrayList<>();
        ChessPiece.ChessColor opponentColor = ChessPiece.getOpponentColor(board.get(rowCC(Integer.parseInt(position.substring(1)), position.charAt(0) - 96)).getColor()); //TODO benjoe refaktoráld péeles :*
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getColor() == opponentColor && board.get(i).getType() != ChessPiece.ChessType.KING){
                attackedFields.addAll(calculateMoves(board.get(i), positionToCode(i), board, true));
            }
        }
        Log.d(TAG, "attackedFields: " + attackedFields.size());
        return attackedFields;
    }

    public static boolean inCheck (ArrayList<ChessPiece> board, ChessPiece.ChessColor color){
        String kingPosition = "";
        for (int i = 0; i < board.size(); i++){
            if (board.get(i).getType() == ChessPiece.ChessType.KING && board.get(i).getColor() == color){
                kingPosition = positionToCode(i);
            }
        }
        ArrayList<String> dangerZone = attackedFields(kingPosition, board);
        return dangerZone.contains(kingPosition);
    }



}

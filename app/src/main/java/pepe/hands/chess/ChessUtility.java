package pepe.hands.chess;

import android.util.Log;

import java.util.ArrayList;

public class ChessUtility {
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

    public static ArrayList<ChessPiece> putDownPiece(ArrayList<ChessPiece> table, ChessPiece piece, String... Positions) {
        for (String position : Positions) {
            table.set(codeToPosition(position), piece);
        }
        return table;
    }

    public static ArrayList<String> calculateMoves(ChessPiece chessPiece, String position, ArrayList<ChessPiece> board) {
        ArrayList<String> moves = new ArrayList<>();
        switch (chessPiece.getType()) {
            case PAWN: {
                moves = calculatePawn(position, board, chessPiece);
                break;
            }
            case ROOK: {
                moves = calculateRook(position, board);
                break;
            }
            case KNIGHT: {
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
                break;
            }
        }

        return moves;
    }

    private static ArrayList<String> calculateBishop(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> bishopMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        boolean upLeft, downRight, downLeft, upRight;
        upLeft = downRight = downLeft = upRight = true;

        Log.d(TAG, "tempPos: row: "+row + "__col: " + column);
        for (int i = 1; (upLeft || downRight || downLeft || upRight); i++) {
            if (column - i >= 1 && row - i >= 1 && downLeft){
                int tempPosition = rowCC(row - i, column-i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop1: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    Log.d(TAG, "calculatebishop2: "+ ChessUtility.positionToCode(tempPosition));
                    downLeft = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop3: "+ ChessUtility.positionToCode(tempPosition));
                    downLeft = false;
                }
            }
            else{
                downLeft = false;
            }

            if (column+i <= 8 && row + i <= 8 && upRight){
                int tempPosition = rowCC(row + i, column+i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    upRight = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                    upRight = false;
                }
            }
            else{
                upRight = false;
            }
            if (row + i  <= 8 && column - i >= 1 && upLeft){
                int tempPosition = rowCC(row+i, column - i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    upLeft = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                    upLeft = false;
                }
            }
            else{
                upLeft = false;
            }
            if (row - i >= 1 && column + i <= 8 && downRight){
                int tempPosition = rowCC(row-i, column + i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    downRight = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    bishopMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculatebishop: "+ ChessUtility.positionToCode(tempPosition));
                    downRight = false;
                }
            }
            else{
                downRight = false;
            }
        }

        return bishopMoves;
    }
    
    private static ArrayList<String> calculateRook(String position, ArrayList<ChessPiece> board) {
        ArrayList<String> rookMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        boolean up, down, left, right;
        up = down = left = right = true;

        Log.d(TAG, "tempPos: row: "+row + "__col: " + column);
        for (int i = 1; (up || down || left || right); i++) {
            if (column-i >= 1  && left){
                int tempPosition = rowCC(row, column-i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook1: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    Log.d(TAG, "calculateRook2: "+ ChessUtility.positionToCode(tempPosition));
                    left = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook3: "+ ChessUtility.positionToCode(tempPosition));
                    left = false;
                }
            }
            else{
                left = false;
            }

            if (column+i <= 8 && right){
                int tempPosition = rowCC(row, column+i);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    right = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                    right = false;
                }
            }
            else{
                right = false;
            }
            if (row + i  <= 8 && up){
                int tempPosition = rowCC(row+i, column);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    up = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                    up = false;
                }
            }
            else{
                up = false;
            }
            if (row - i >= 1 && down){
                int tempPosition = rowCC(row-i, column);
                //TODO SWitch (ha kezeli a null-t)
                if (board.get(tempPosition).getColor() == null){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                }
                if (board.get(tempPosition).getColor() == board.get(ChessUtility.codeToPosition(position)).getColor()){
                    down = false;
                }
                if (board.get(tempPosition).getColor() != null && board.get(tempPosition).getColor() != board.get(ChessUtility.codeToPosition(position)).getColor()){
                    rookMoves.add(ChessUtility.positionToCode(tempPosition));
                    Log.d(TAG, "calculateRook: "+ ChessUtility.positionToCode(tempPosition));
                    down = false;
                }
            }
            else{
                down = false;
            }
        }

        return rookMoves;
    }

    private static ArrayList<String> calculatePawn(String position, ArrayList<ChessPiece> board, ChessPiece chessPiece) {
        ArrayList<String> pawnMoves = new ArrayList<>();
        int column = position.charAt(0) - 96;
        int row = Integer.parseInt(position.substring(1));
        int tempPosition;
        if (chessPiece.getColor() == ChessPiece.ChessColor.WHITE){
            tempPosition = rowCC(row + 1, column);
            if (board.get(tempPosition).getColor() == null){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
            if (row == 2) {
                tempPosition = rowCC(row + 2, column);
                if (board.get(tempPosition).getColor() == null) {
                    pawnMoves.add(ChessUtility.positionToCode(tempPosition));
                }
            }
            tempPosition = rowCC(row + 1, column - 1);
            if (column >= 1 && board.get(tempPosition).getColor() == ChessPiece.ChessColor.BLACK){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
            tempPosition = rowCC(row + 1, column + 1);
            if (column <= 8 && board.get(tempPosition).getColor() == ChessPiece.ChessColor.BLACK){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
        }
        if (chessPiece.getColor() == ChessPiece.ChessColor.BLACK){
            tempPosition = rowCC(row - 1, column);
            if (board.get(tempPosition).getColor() == null){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
            if (row == 7) {
                tempPosition = rowCC(row - 2, column);
                if (board.get(tempPosition).getColor() == null) {
                    pawnMoves.add(ChessUtility.positionToCode(tempPosition));
                }
            }
            tempPosition = rowCC(row - 1, column - 1);
            if (column >= 1 && board.get(tempPosition).getColor() == ChessPiece.ChessColor.WHITE){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
            tempPosition = rowCC(row - 1, column + 1);
            if (column <= 8 && board.get(tempPosition).getColor() == ChessPiece.ChessColor.WHITE){
                pawnMoves.add(ChessUtility.positionToCode(tempPosition));
            }
        }


        return pawnMoves;
    }
}

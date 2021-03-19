package pepe.hands.chess;

public class ChessPiece {
    public enum ChessColor {
        WHITE(0), BLACK(1);

        public final int label;

        ChessColor(int label) {
            this.label = label;
        }
    }

    public enum ChessType {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    private ChessColor color;
    private ChessType type;
    private char picture;
    private boolean moved = false;

    public ChessPiece(ChessColor color, ChessType type) {
        this.color = color;
        this.type = type;
        setPicture();
    }

    public ChessPiece() {
    }

    public ChessColor getColor() {
        return color;
    }

    public ChessType getType() {
        return type;
    }

    public char getPicture() {
        return picture;
    }

    public boolean isMoved() { return moved; }

    private void setPicture() {
        if (this.color.equals(ChessColor.WHITE)) {
            switch (type){
                case PAWN : {
                    this.picture = '\u2659';
                    break;
                }
                case KNIGHT : {
                    this.picture = '\u2658';
                    break;
                }
                case BISHOP : {
                    this.picture = '\u2657';
                    break;
                }
                case ROOK : {
                    this.picture = '\u2656';
                    break;
                }
                case QUEEN : {
                    this.picture = '\u2655';
                    break;
                }
                case KING : {
                    this.picture = '\u2654';
                    break;
                }
            }

        } else if (this.color.equals(ChessColor.BLACK)) {
            switch (type){
                case PAWN : {
                    this.picture = '\u265F';
                    break;
                }
                case KNIGHT : {
                    this.picture = '\u265E';
                    break;
                }
                case BISHOP : {
                    this.picture = '\u265D';
                    break;
                }
                case ROOK : {
                    this.picture = '\u265C';
                    break;
                }
                case QUEEN : {
                    this.picture = '\u265B';
                    break;
                }
                case KING : {
                    this.picture = '\u265A';
                    break;
                }
            }
        }
    }

    public void SetMoved() { moved = true; }
}

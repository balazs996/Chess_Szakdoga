package pepe.hands.chess;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ChessPiece> pieces = new ArrayList<>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupStartboard();

        GridView gv = findViewById(R.id.gridview);
        gv.setAdapter(new ChessAdapter(MainActivity.this, R.layout.item_board, pieces));
    }
    private void setupStartboard(){
        for (int i = 0; i < 64; i++) {
            pieces.add(new ChessPiece());

        }
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.PAWN), "a2", "b2","c2","d2","e2","f2","g2","h2");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.PAWN), "a7", "b7","c7","d7","e7","f7","g7","h7");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.ROOK), "a1", "h1");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.ROOK), "a8", "h8");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.KNIGHT), "b1", "g1");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.KNIGHT), "b8", "g8");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.BISHOP), "c1", "f1");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.BISHOP), "c8", "f8");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.KING), "e1");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.KING), "e8");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.QUEEN), "d1");
        pieces = ChessConverter.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.QUEEN), "d8");
    }
}
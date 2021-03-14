package pepe.hands.chess;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ChessPiece> pieces = new ArrayList<>();
    ChessAdapter chessAdapter;
    int clickedPosition = -1;
    GridView gv;
    private static final String TAG = "MainActivity";
    AppCompatButton reset;
    TextView status;
    boolean isWhiteTurn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = findViewById(R.id.gridview);
        setupStartboard();
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupStartboard();
                Log.d(TAG, "onClick: ");
            }
        });
        
        status = findViewById(R.id.status);
        

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + ChessUtility.positionToCode(position));
                if (clickedPosition == -1){
                    if ((chessAdapter.getPiece(position).getColor() == ChessPiece.ChessColor.WHITE && isWhiteTurn) || (chessAdapter.getPiece(position).getColor() == ChessPiece.ChessColor.BLACK && !isWhiteTurn)){
                        clickedPosition = position;
                        gv.getChildAt(position).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pörpöl));
                        for (String tempPosition:ChessUtility.calculateMoves(chessAdapter.getPiece(position), ChessUtility.positionToCode(position), pieces)) {
                            gv.getChildAt(ChessUtility.codeToPosition(tempPosition)).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.hugyosfos));
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Másik játékos lép!", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (clickedPosition == position) {
                    clickedPosition = -1;
                    //Toast.makeText(MainActivity.this, "Szabálytalan lépés!", Toast.LENGTH_SHORT).show();
                    gv.getChildAt(position).setBackgroundTintList(null);
                    for (String tempPosition:ChessUtility.calculateMoves(chessAdapter.getPiece(position), ChessUtility.positionToCode(position), pieces)) {
                        gv.getChildAt(ChessUtility.codeToPosition(tempPosition)).setBackgroundTintList(null);
                    }

                }
                else {
                    if (ChessUtility.calculateMoves(chessAdapter.getPiece(clickedPosition), ChessUtility.positionToCode(clickedPosition),pieces).contains(ChessUtility.positionToCode(position))) {
                        chessAdapter.movePiece(clickedPosition, position);
                        clickedPosition = -1;
                        isWhiteTurn = !isWhiteTurn;
                        gv.getChildAt(position).setBackgroundTintList(null);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Szabálytalan lépés!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setupStartboard(){
        pieces = new ArrayList<>();
        isWhiteTurn = true;
        for (int i = 0;
             i < 64;
             i++) {
            pieces.add(new ChessPiece());

        }
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.PAWN), "a2", "b2","c2","d2","e2","f2","g2","h2");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.PAWN), "a7", "b7","c7","d7","e7","f7","g7","h7");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.ROOK), "a1", "h1");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.ROOK), "a8", "h8");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.KNIGHT), "b1", "g1");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.KNIGHT), "b8", "g8");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.BISHOP), "c1", "f1");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.BISHOP), "c8", "f8");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.KING), "e1");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.KING), "e8");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.WHITE, ChessPiece.ChessType.QUEEN), "d1");
        pieces = ChessUtility.putDownPiece(pieces, new ChessPiece(ChessPiece.ChessColor.BLACK, ChessPiece.ChessType.QUEEN), "d8");
        chessAdapter = new ChessAdapter(MainActivity.this, R.layout.item_board, pieces);
        //chessAdapter.notifyDataSetChanged();
        gv.setAdapter(chessAdapter);

        /*
        For és Foreach

        for (ChessPiece piece: pieces) {
            Log.d(TAG, "setupStartboard: " + piece.type);
        }
        for (int i = 0; i < pieces.size(); i=+2) {
            Log.d(TAG, "setupStartboard: " + pieces.get(i).type);
            for (int j = 0; j < ; j++) {

            }

        }
         */
    }

}





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
    private int currentPlayer;
    private ArrayList<String> availablePositions = new ArrayList<>();
    
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
                ChessPiece.ChessColor pieceColor = chessAdapter.getPiece(position).getColor();
                if (pieceColor != null && pieceColor.label == currentPlayer) {
                    for (String tempPosition:availablePositions) {
                        gv.getChildAt(ChessUtility.codeToPosition(tempPosition)).setBackgroundTintList(null);
                    }
                    if(clickedPosition > 0) gv.getChildAt(clickedPosition).setBackgroundTintList(null);

                    clickedPosition = position;
                    availablePositions = ChessUtility.calculateMoves(chessAdapter.getPiece(position), ChessUtility.positionToCode(position), pieces);
                    gv.getChildAt(position).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pörpöl));
                    for (String tempPosition:availablePositions) {
                        gv.getChildAt(ChessUtility.codeToPosition(tempPosition)).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.hugyosfos));
                    }
                }
                else if(availablePositions.contains(ChessUtility.positionToCode(position))) {
                    chessAdapter.movePiece(clickedPosition, position);
                    clickedPosition = -1;
                    availablePositions.clear();
                    currentPlayer = (currentPlayer + 1) % 2;
                    gv.getChildAt(position).setBackgroundTintList(null);
                }
                else {
                    String errorMsg;
                    if(pieceColor != null) errorMsg = "Másik játékos lép!";
                    else errorMsg = "Szabálytalan lépés!";
                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setupStartboard(){
        pieces = new ArrayList<>();
        currentPlayer = 0;
        availablePositions.clear();
        for (int i = 0;
             i < 64;
             i++) {
            pieces.add(new ChessPiece());

        }
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.PAWN, "a", "b","c","d","e","f","g","h");
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.ROOK, "a", "h");
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.KNIGHT, "b", "g");
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.BISHOP, "c", "f");
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.KING, "e");
        pieces = ChessUtility.putDownPiece(pieces, ChessPiece.ChessType.QUEEN, "d");
        chessAdapter = new ChessAdapter(MainActivity.this, R.layout.item_board, pieces);
        gv.setAdapter(chessAdapter);

    }

}





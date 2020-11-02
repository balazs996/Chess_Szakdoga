package pepe.hands.chess;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> pieces = new ArrayList<>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 64; i++) {
            pieces.add("1");

        }


        GridView gv = findViewById(R.id.gridview);
        gv.setAdapter(new ChessAdapter(MainActivity.this, R.layout.item_board, pieces));
    }
}
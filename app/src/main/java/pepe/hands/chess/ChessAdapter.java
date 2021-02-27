package pepe.hands.chess;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChessAdapter extends ArrayAdapter {
        ArrayList<String> pieces;

    public ChessAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        pieces = objects;


    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_board, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.boardcell);

        int col = position % 8 + 1;
        int row = position / 8 + 1;
        boolean idCheck = (col % 2 == 0 && row % 2 == 0) || (col % 2 != 0 && row % 2 != 0);
        int id = idCheck ? R.color.colorBoardWhite : R.color.colorBoardBlack;
        textView.setBackgroundColor(getContext().getResources().getColor(id));

        int width = getContext().getResources().getDisplayMetrics().widthPixels / 8;
        v.setLayoutParams(new GridView.LayoutParams(width, width));

        return v;
    }
}

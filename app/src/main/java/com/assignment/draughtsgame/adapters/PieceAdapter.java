package com.assignment.draughtsgame.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.assignment.draughtsgame.R;


public class PieceAdapter extends BaseAdapter {
    private Context context;
    private final String[] dataValues;
    private boolean blackFirst=true;
    private int cellCount=0;

    public PieceAdapter(Context context, String[] dataValues) {
        this.context = context;
        this.dataValues = dataValues;
    }
    @Override
    public int getCount() {
        return dataValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        String[] vec_tags = {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8",
                "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7",
                "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6",
                "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5",
                "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4",
                "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3",
                "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2",
                "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"};

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);


        if (convertView == null) {
            // get layout from data.xml
            gridView = inflater.inflate(R.layout.piece, null);

            // set image based on selected text
            ImageView imageView =gridView.findViewById(R.id.grid_piece);
            renderBoard(position, imageView, sharedPref);
            initPiece(sharedPref);
            setPiece(position, imageView);

            imageView.setTag(vec_tags[position]);

        } else {
            gridView=convertView;
        }

        return gridView;
    }

    private void renderBoard(int position, ImageView imageView, SharedPreferences sharedPreferences){
        if(cellCount>7){
            cellCount=0;
            blackFirst= !blackFirst;
        }
        if(blackFirst) {
            if (position % 2 == 0)
                imageView.setBackgroundColor(context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.prelim_cell_key), R.color.defaultPrelimCellColor)));
            else
                imageView.setBackgroundColor(context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.game_cell_key), R.color.defaultGameCellColor)));
        }else {
            if (position % 2 == 0)
                imageView.setBackgroundColor(context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.game_cell_key), R.color.defaultGameCellColor)));
            else
                imageView.setBackgroundColor(context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.prelim_cell_key), R.color.defaultPrelimCellColor)));
        }
        cellCount++;
    }

    private void initPiece(SharedPreferences sharedPreferences){
        Drawable unwrappedP1Drawable = AppCompatResources.getDrawable(context, R.drawable.player_one_piece);
        assert unwrappedP1Drawable != null;
        Drawable wrappedP1Drawable = DrawableCompat.wrap(unwrappedP1Drawable);
        DrawableCompat.setTint(wrappedP1Drawable, context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.player_one_piece_key), R.color.playerOneDefaultPiece)));

        Drawable unwrappedP1KingDrawable = AppCompatResources.getDrawable(context, R.drawable.player_one_piece_crowned);
        assert unwrappedP1KingDrawable != null;
        Drawable wrappedP1KingDrawable = DrawableCompat.wrap(unwrappedP1KingDrawable);
        DrawableCompat.setTint(wrappedP1KingDrawable, context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.player_one_piece_key), R.color.playerOneDefaultPiece)));

        Drawable unwrappedP2Drawable = AppCompatResources.getDrawable(context, R.drawable.player_two_piece);
        assert unwrappedP2Drawable != null;
        Drawable wrappedP2Drawable = DrawableCompat.wrap(unwrappedP2Drawable);
        DrawableCompat.setTint(wrappedP2Drawable, context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)));

        Drawable unwrappedP2KingDrawable = AppCompatResources.getDrawable(context, R.drawable.player_two_piece_crowned);
        assert unwrappedP2KingDrawable != null;
        Drawable wrappedP2KingDrawable = DrawableCompat.wrap(unwrappedP2KingDrawable);
        DrawableCompat.setTint(wrappedP2KingDrawable, context.getResources().getColor(sharedPreferences.getInt(context.getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)));
    }

    private void setPiece(int position, ImageView imageView){
        String data;
        data = dataValues[position];

        if (data.equals("b")) {
            imageView.setImageResource(R.drawable.player_two_piece);
        } else if (data.equals("w")) {
            imageView.setImageResource(R.drawable.player_one_piece);
        } else if (data.equals("B")) {
            if (position != 74) {
                imageView.setImageResource(R.drawable.player_two_piece_crowned);
            } else {
                imageView.setVisibility(View.GONE);
            }
        } else if (data.equals("W")) {
            imageView.setImageResource(R.drawable.player_one_piece_crowned);
        } else if (data.equals(".")) {
            imageView.setImageResource(0);
        } else if (data.equals("b\n")) {
            imageView.setImageResource(R.drawable.player_two_piece);
        } else if (data.equals("w\n")) {
            imageView.setImageResource(R.drawable.player_one_piece);
        } else if (data.equals(".\n")) {
            imageView.setImageResource(R.drawable.player_two_piece);
        } else {
            imageView.setImageResource(R.drawable.player_one_piece);
        }
    }
}

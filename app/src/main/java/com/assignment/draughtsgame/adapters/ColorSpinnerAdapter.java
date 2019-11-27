package com.assignment.draughtsgame.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;


import com.assignment.draughtsgame.R;

import java.util.List;

public class ColorSpinnerAdapter extends ArrayAdapter<String> {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Integer> items;
    private final int mResource;
    private Boolean board;

    public ColorSpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                               @NonNull List objects, Boolean board) {
        super(context, resource, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
        this.board=board;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        ImageView colorView=view.findViewById(R.id.color);

        if(board) {
            colorView.setBackgroundColor(mContext.getResources().getColor(items.get(position)));
        }else {
            Drawable unwrappedP1Drawable = AppCompatResources.getDrawable(mContext, R.drawable.player_one_piece);
            assert unwrappedP1Drawable != null;
            Drawable wrappedP1Drawable = DrawableCompat.wrap(unwrappedP1Drawable);
            DrawableCompat.setTint(wrappedP1Drawable, mContext.getResources().getColor(items.get(position)));
            colorView.setImageDrawable(wrappedP1Drawable);
            colorView.setBackgroundColor(0);
        }

        return view;
    }
}

package com.assignment.draughtsgame.activities;

import android.os.Bundle;
import android.widget.GridView;

import com.assignment.draughtsgame.R;
import com.assignment.draughtsgame.adapters.PieceAdapter;
import com.assignment.draughtsgame.utils.DraughtsBoard;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    DraughtsBoard draughtsBoard=new DraughtsBoard();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String[] myvec=draughtsBoard.vec_string();
        gridView=findViewById(R.id.gridview);
        gridView.setAdapter(new PieceAdapter(this, myvec));
    }
}

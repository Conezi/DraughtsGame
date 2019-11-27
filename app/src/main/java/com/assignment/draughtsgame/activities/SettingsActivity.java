package com.assignment.draughtsgame.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.assignment.draughtsgame.R;
import com.assignment.draughtsgame.adapters.ColorSpinnerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private Spinner activeCell, inactiveCell, playerOnePiece, playerTwoPiece;
    private List<Integer> colorList;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            setResultAndFinish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        final Integer[] colors={R.color.playerOneDefaultPiece, R.color.playerTwoDefaultPiece, R.color.defaultGameCellColor, R.color.defaultPrelimCellColor, R.color.yellow, R.color.green, R.color.brown, R.color.blue, R.color.red, R.color.purple, R.color.orange, R.color.pink, R.color.navy, R.color.grey};
        colorList=Arrays.asList(colors);

        activeCell=findViewById(R.id.activeCell);
        inactiveCell=findViewById(R.id.inactiveCell);
        final ColorSpinnerAdapter colorSpinnerAdapter=new ColorSpinnerAdapter(this, R.layout.color_element, colorList, true);
        activeCell.setAdapter(colorSpinnerAdapter);
        inactiveCell.setAdapter(colorSpinnerAdapter);

        playerOnePiece=findViewById(R.id.player_one_piece);
        playerTwoPiece=findViewById(R.id.player_two_piece);
        ColorSpinnerAdapter colorSpinnerAdapter1=new ColorSpinnerAdapter(this, R.layout.color_element, colorList, false);
        playerOnePiece.setAdapter(colorSpinnerAdapter1);
        playerTwoPiece.setAdapter(colorSpinnerAdapter1);
        initColorPicker();


        activeCell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!(sharedPref.getInt(getString(R.string.player_one_piece_key),R.color.playerOneDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.prelim_cell_key), R.color.defaultPrelimCellColor)==colorList.get(position))) {
                    editor.putInt(getString(R.string.game_cell_key), colorList.get(position));
                    editor.apply();
                }else {
                    Toast.makeText(SettingsActivity.this, "Already in use", Toast.LENGTH_SHORT).show();
                    activeCell.setSelection(colorList.indexOf(sharedPref.getInt(getString(R.string.game_cell_key), R.color.defaultGameCellColor)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inactiveCell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(sharedPref.getInt(getString(R.string.player_one_piece_key),R.color.playerOneDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.game_cell_key), R.color.defaultGameCellColor)==colorList.get(position))) {
                    editor.putInt(getString(R.string.prelim_cell_key), colorList.get(position));
                    editor.apply();
                }else {
                    Toast.makeText(SettingsActivity.this, "Already in use", Toast.LENGTH_SHORT).show();
                    inactiveCell.setSelection(colorList.indexOf(sharedPref.getInt(getString(R.string.prelim_cell_key), R.color.defaultPrelimCellColor)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        playerOnePiece.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(sharedPref.getInt(getString(R.string.prelim_cell_key),R.color.defaultPrelimCellColor)==colorList.get(position)||sharedPref.getInt(getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.game_cell_key), R.color.defaultGameCellColor)==colorList.get(position))) {
                    editor.putInt(getString(R.string.player_one_piece_key), colorList.get(position));
                    editor.apply();
                }else {
                    Toast.makeText(SettingsActivity.this, "Already in use", Toast.LENGTH_SHORT).show();
                    playerOnePiece.setSelection(colorList.indexOf(sharedPref.getInt(getString(R.string.player_one_piece_key), R.color.playerOneDefaultPiece)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        playerTwoPiece.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(sharedPref.getInt(getString(R.string.prelim_cell_key),R.color.defaultPrelimCellColor)==colorList.get(position)||sharedPref.getInt(getString(R.string.player_one_piece_key), R.color.playerOneDefaultPiece)==colorList.get(position)||sharedPref.getInt(getString(R.string.game_cell_key), R.color.defaultGameCellColor)==colorList.get(position))) {
                    editor.putInt(getString(R.string.player_two_piece_key), colorList.get(position));
                    editor.apply();
                }else {
                    Toast.makeText(SettingsActivity.this, "Already in use", Toast.LENGTH_SHORT).show();
                    playerTwoPiece.setSelection(colorList.indexOf(sharedPref.getInt(getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initColorPicker(){
        for(int item: colorList){
            if(item==sharedPref.getInt(getString(R.string.game_cell_key), R.color.defaultGameCellColor)){
                activeCell.setSelection(colorList.indexOf(item));
            }
            if(item==sharedPref.getInt(getString(R.string.prelim_cell_key), R.color.defaultPrelimCellColor)){
                inactiveCell.setSelection(colorList.indexOf(item));
            }
            if(item==sharedPref.getInt(getString(R.string.player_one_piece_key), R.color.playerOneDefaultPiece)){
                playerOnePiece.setSelection(colorList.indexOf(item));
            }
            if(item==sharedPref.getInt(getString(R.string.player_two_piece_key), R.color.playerTwoDefaultPiece)){
                playerTwoPiece.setSelection(colorList.indexOf(item));
            }
        }

    }

    private void setResultAndFinish(){
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResultAndFinish();
        return true;
    }

    @Override
    public void onBackPressed() {
        setResultAndFinish();
        super.onBackPressed();
    }
}

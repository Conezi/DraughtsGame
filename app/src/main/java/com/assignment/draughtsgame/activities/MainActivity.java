package com.assignment.draughtsgame.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.draughtsgame.R;
import com.assignment.draughtsgame.adapters.PieceAdapter;
import com.assignment.draughtsgame.anims.Animations;
import com.assignment.draughtsgame.utils.DraughtsBoard;
import com.assignment.draughtsgame.utils.DraughtsMove;
import com.assignment.draughtsgame.utils.DraughtsPiece;
import com.assignment.draughtsgame.utils.DraughtsPosition;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    int switcher = 1;
    private String newPositionTag;
    String click_move = "empty";
    private GridView gridView;
    private TextView playerOne, playerTwo, turn;
    private View previousView =null;
    private BottomSheetDialog dialog;
    public static final int REQUEST_CODE = 1;
    DraughtsBoard draughtsBoard=new DraughtsBoard();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] myvec=draughtsBoard.vec_string();
        gridView=findViewById(R.id.gridview);
        gridView.setAdapter(new PieceAdapter(this, myvec));

        playerOne=findViewById(R.id.player_one_piece);
        playerTwo=findViewById(R.id.player_two_piece);
        turn=findViewById(R.id.turn);

    }

    public void piece_pressed(View view) {
        String s = (String)view.getTag();
        boolean doubleChance=(s.equals(newPositionTag));
        if(draughtsBoard.playAgain){
            if(doubleChance) {
                clearSelection();
                selectOrMovePiece(view, s);
                draughtsBoard.setPlayAgain(false);
            }else {
                if(!click_move.equals("")){
                    selectOrMovePiece(view, s);
                    return;
                }
                draughtsBoard.setPlayAgain(false);
                draughtsBoard.turn=draughtsBoard.turn*-1;
                //Toast.makeText(this, "Player missed multiple capture", Toast.LENGTH_SHORT).show();
            }
        }else {
            selectOrMovePiece(view,s);
        }
    }

    private void setPlayersPieceCount(){
        //Player's remaining piece
        draughtsBoard.winner();
        String[] playersPiece=draughtsBoard.playersPiece().split(",");
        playerOne.setText(playersPiece[0]);
        playerTwo.setText(playersPiece[1]);

        if(playersPiece[0].equals("0")){
            showWinDialog("Player two wins");
            //Toast.makeText(this, "Player two wins", Toast.LENGTH_SHORT).show();
        }else if(playersPiece[1].equals("0")){
            showWinDialog("Player one wins");
            //Toast.makeText(this, "Player one wins", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectOrMovePiece(View view, String s){
        if (switcher == 1) {
            System.out.println(s);
            DraughtsPosition dp = new DraughtsPosition();
            dp.position_parse(s);
            new DraughtsPiece();
            DraughtsPiece pp = draughtsBoard.board_get_piece_at(dp.row, dp.col);
            if (draughtsBoard.turn == 1) {
                if (pp.toString().equals("w")) {
                    onViewSelected(view, s);
                } else if (pp.toString().equals("W")) {
                    onViewSelected(view, s);
                } else {
                    Toast.makeText(this, "Player Ones's turn!", Toast.LENGTH_SHORT).show();
                }
            } else if (pp.toString().equals("b")) {
                onViewSelected(view, s);
            } else if (pp.toString().equals("B")) {
                onViewSelected(view, s);
            } else {
                Toast.makeText(this, "Player Two's turn!", Toast.LENGTH_SHORT).show();
            }
        } else {
            click_move = click_move + "-" + s;
            System.out.println(click_move);
            int j = draughtsBoard.board_move_and_capture(new DraughtsMove(click_move));
            if (j == -1) {
                Toast.makeText(this, "Invalid Move, try again!", Toast.LENGTH_SHORT).show();
                clearSelection();
            } else {
                newPositionTag=(String)view.getTag();
                String[] myvec=draughtsBoard.vec_string();
                gridView = findViewById(R.id.gridview);
                gridView.setAdapter(new PieceAdapter(this, myvec));
                clearSelection();
                draughtsBoard.board_print();
                setPlayersPieceCount();
                turn.setText(getPlaying());

                if(draughtsBoard.playAgain){
                    onViewSelected(view, s);
                }
            }
        }
    }

    private void clearSelection(){
        switcher = 1;
        click_move = "";

        if(previousView !=null) {
            new Animations().Scale(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, 200, 0, 0, previousView);
            previousView =null;
        }
    }
    private void onViewSelected(View view, String viewTag){
        click_move = viewTag;
        switcher = -1;
        new Animations().Scale(1f,1.1f,1f,1.1f, Animation.RELATIVE_TO_SELF,Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, 200, 0, 0, view);

        if(previousView !=null) {
            new Animations().Scale(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, 200, 0, 0, previousView);
        }
        previousView =view;
    }

    private String getPlaying(){
        if(draughtsBoard.turn==1){
            return "PLAYER ONE'S TURN";
        }else {
            return "PLAYER TWO'S TURN";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showBottomSheet(View view) {
        // using BottomSheetDialog
        View dialogView = getLayoutInflater().inflate(R.layout.pause_buttom_sheet, null);
        dialog = new BottomSheetDialog(this, R.style.bottom_sheet_style);
        dialog.setContentView(dialogView);

        Button reset=dialog.findViewById(R.id.reset);
        Objects.requireNonNull(reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        Button settings=dialog.findViewById(R.id.settings);
        Objects.requireNonNull(settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), REQUEST_CODE);
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
            String[] myvec=draughtsBoard.vec_string();
            gridView = findViewById(R.id.gridview);
            gridView.setAdapter(new PieceAdapter(this, myvec));
            dialog.cancel();
        }
    }

    private void showWinDialog(String message){
        try {
            final AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(message);
            adb.setIcon(android.R.drawable.alert_dark_frame);
            adb.setCancelable(false);
            adb.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(getIntent());
                }
            });
            adb.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            adb.show();
        } catch (Exception e) {
            Log.d("DIALOG", "Show Dialog: " + e.getMessage());
        }
    }
}


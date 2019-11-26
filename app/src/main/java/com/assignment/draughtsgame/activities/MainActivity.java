package com.assignment.draughtsgame.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.Toast;

import com.assignment.draughtsgame.R;
import com.assignment.draughtsgame.adapters.PieceAdapter;
import com.assignment.draughtsgame.anims.Animations;
import com.assignment.draughtsgame.utils.DraughtsBoard;
import com.assignment.draughtsgame.utils.DraughtsMove;
import com.assignment.draughtsgame.utils.DraughtsPiece;
import com.assignment.draughtsgame.utils.DraughtsPosition;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int switcher = 1;
    String click_move = "empty";
    private View previousView =null;
    private GridView gridView;
    private String newPositionTag;
    DraughtsBoard draughtsBoard=new DraughtsBoard();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String[] myvec=draughtsBoard.vec_string();
        gridView=findViewById(R.id.gridview);
        gridView.setAdapter(new PieceAdapter(this, myvec));
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

}

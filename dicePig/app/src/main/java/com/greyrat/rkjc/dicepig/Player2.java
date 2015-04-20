package com.greyrat.rkjc.dicepig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class Player2 extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll;
    private Button hold;
    private int player1score = 0, player2score = 0;
    private int die1val = 0, die2val = 0;
    private int rScore = 0;
    private TextView roundScore, usScore2, themScore2;
    private boolean newGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);

        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);
        roundScore = (TextView) findViewById(R.id.roundScore);
        usScore2 = (TextView) findViewById(R.id.usScore2);
        themScore2 = (TextView) findViewById(R.id.themScore2);

        Bundle bundle = getIntent().getExtras();
        player1score = bundle.getInt("player1score");
        player2score = bundle.getInt("player2score");

        usScore2.setText(Integer.toString(player2score));
        themScore2.setText(Integer.toString(player1score));


        roll = (Button) findViewById(R.id.roll_button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newGame){
                    player1score = 0; player2score = 0;
                    die1val = 0; die2val = 0;
                    rScore = 0;
                    newGame = false;
                    usScore2.setText(Integer.toString(player2score));
                    themScore2.setText(Integer.toString(player1score));
                }

                rollDice();

                if(die1val == 1 || die2val == 1){
                    failedRoll();
                }

                rScore = rScore + die1val + die2val;
                roundScore.setText(Integer.toString(rScore));

                //test for win condition
                if(rScore + player2score >= 100){
                    usScore2.setText(Integer.toString(rScore + player2score));

                    AlertDialog alertDialog = new AlertDialog.Builder(Player2.this).create();
                    alertDialog.setTitle("You Won!");
                    alertDialog.setMessage("Yipeeaieahhh!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    newGame = true;
                }
            }
        });

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //add round to usTotal
                player2score += rScore;

                Intent intent = new Intent(Player2.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                intent.putExtra("player1score", player1score);
                intent.putExtra("player2score", player2score);

                startActivity(intent);


            }
        });


    } //end of onCreate()

    public void failedRoll(){
        Intent intent = new Intent(Player2.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        intent.putExtra("player1score", player1score);
        intent.putExtra("player2score", player2score);

        startActivity(intent);
    }

    public void rollDice(){
        int num1 = 1 + (int) (6 * Math.random());
        int num2 = 1 + (int) (6 * Math.random());
        die1val = setDie(num1, die1);
        die2val = setDie(num2, die2);
    }

    public int setDie(int num, FrameLayout layout){
        Drawable pic = null;
        switch(num){
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                layout.setBackground(pic);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                layout.setBackground(pic);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                layout.setBackground(pic);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                layout.setBackground(pic);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                layout.setBackground(pic);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                layout.setBackground(pic);
                break;

            default:
                //Log.i("my code", "Something went wrong");
        }
        layout.setBackground(pic);
        return num;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

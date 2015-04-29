package com.greyrat.rkjc.calculatorx;

import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class AdvActivity extends ActionBarActivity {
    Random rnd = new Random();
    private Button oneKey, sevenKey;
    private Button twoKey;

    private Button mode;
    private double subTotal;
    private TextView display;
    private String displayContent2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_activity);

        Log.d("info-AdvActivity", "entering onCreate");

        display = (TextView) findViewById(R.id.display);


       Bundle bundle = getIntent().getExtras();
       if(bundle != null) {
            displayContent2 = bundle.getString("displayContent");
            subTotal = bundle.getDouble("subTotal");

       } else {
           displayContent2 = "0";
           subTotal = 0;
       }

        Log.d("info-AdvActivity", "pre-print displayContent2");
        Log.d("info-AdvActivity", "printing displayContent2= ");
        Log.d("info-AdvActivity", displayContent2);
        Log.d("info-AdvActivity", "post-print displayContent2");
        display.setText(displayContent2);


        sevenKey = (Button)findViewById(R.id.button7);
        sevenKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("info-AdvActivity", "inside sevenKey listener");
                Log.d("info-AdvActivity", displayContent2);
                displayContent2 = displayContent2 + "7";
                display.setText(displayContent2);
            }
        });

        mode = (Button)findViewById(R.id.mode);
        mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(AdvActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                intent.putExtra("displayContent", displayContent2);
                intent.putExtra("subTotal", subTotal);

                startActivity(intent);
            }
        });

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

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    void setDisplay(String str){
        //if length > 1 and first char == "0" then remove first char

    }


}
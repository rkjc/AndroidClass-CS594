package com.greyrat.rkjc.calculatorx;

import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Debug;
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


public class MainActivity extends ActionBarActivity {
    Random rnd = new Random();
    private Button zeroKey, oneKey, twoKey, threeKey, fourKey;
    private Button fiveKey, sixKey, sevenKey, eightKey, nineKey;
    private Button buttonPlus, buttonMinus, buttonTimes, buttonDivide, buttonDot, buttonEquals;
    private Button buttonClear, buttonDelete;

    private Button mode;
    private double subTotal;
    private TextView display;
    private String displayContent_1 = "0",  displayContent_2 = "";
    private boolean isReal = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zeroKey = (Button)findViewById(R.id.button0);
        oneKey = (Button)findViewById(R.id.button1);
        twoKey = (Button)findViewById(R.id.button2);
        threeKey = (Button)findViewById(R.id.button3);
        fourKey = (Button)findViewById(R.id.button4);
        fiveKey = (Button)findViewById(R.id.button5);
        sixKey = (Button)findViewById(R.id.button6);
        sevenKey = (Button)findViewById(R.id.button7);
        eightKey = (Button)findViewById(R.id.button8);
        nineKey = (Button)findViewById(R.id.button9);

        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        buttonPlus = (Button)findViewById(R.id.buttonPlus);
        buttonMinus = (Button)findViewById(R.id.buttonMinus);
        buttonTimes = (Button)findViewById(R.id.buttonTimes);
        buttonDivide = (Button)findViewById(R.id.buttonDivide);
        buttonEquals = (Button)findViewById(R.id.buttonEquals);
        buttonDot = (Button)findViewById(R.id.buttonDot);

        display = (TextView) findViewById(R.id.display);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            displayContent_1 = bundle.getString("displayContent_1");
            displayContent_2 = bundle.getString("displayContent_2");
            subTotal = bundle.getDouble("subTotal");
        }

        display.setText(displayContent_2 + "\n" + displayContent_1);



        mode = (Button)findViewById(R.id.mode);
        mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(MainActivity.this,AdvActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Log.d("info-Main", "passing through mode listener");
                Log.d("info-Main", "displayContent_1= " + displayContent_1);

                intent.putExtra("displayContent_1", displayContent_1);
                intent.putExtra("subTotal", subTotal);


                startActivity(intent);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                displayContent_1 = "0";
                displayContent_2 = "";
                subTotal = 0;
                isReal = false;
                display.setText(displayContent_2 + "\n" + displayContent_1);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(displayContent_1.length() > 1) {
                    displayContent_1 = displayContent_1.substring(0, displayContent_1.length() - 1);
                }else{
                    displayContent_1 = "0";
                }
                display.setText(displayContent_2 + "\n" + displayContent_1);
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setFunction("+"); }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setFunction("-"); }
        });
        buttonTimes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setFunction("X"); }
        });
        buttonDivide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setFunction("/"); }
        });



        buttonEquals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

        zeroKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("0"); }
        });
        oneKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("1"); }
        });
        twoKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("2"); }
        });
        threeKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("3"); }
        });
        fourKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("4"); }
        });
        fiveKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("5"); }
        });
        sixKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("6"); }
        });
        sevenKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("7"); }
        });
        eightKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("8"); }
        });
        nineKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("9"); }
        });
        buttonDot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setDisplay("."); }
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
        //check if second char is dot, don't suppress leading zero
        if(str.equals(".") && !isReal){
            isReal = true;
            displayContent_1 = displayContent_1 + str;
            display.setText(displayContent_2 + "\n" + displayContent_1);
        }
        else if(displayContent_1.length() == 1 && displayContent_1.startsWith("0")){
            displayContent_1 = str;
        }
        else {
            displayContent_1 = displayContent_1 + str;
        }
        display.setText(displayContent_2 + "\n" + displayContent_1);
    }

    void setFunction(String str){
        displayContent_2 = (displayContent_1 + " " + str);
        displayContent_1 = "";
        display.setText(displayContent_2 + "\n" + displayContent_1);
    }

}
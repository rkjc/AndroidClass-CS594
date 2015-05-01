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
    //declare variables
    Random rnd = new Random();
    private Button zeroKey, oneKey, twoKey, threeKey, fourKey;
    private Button fiveKey, sixKey, sevenKey, eightKey, nineKey;
    private Button buttonPlus, buttonMinus, buttonTimes, buttonDivide, buttonDot, buttonEquals;
    private Button buttonClear, buttonDelete;

    private Button mode;
    private double subTotal;
    private TextView display;
    private String displayContent_1 = "0",  displayContent_2 = "", functionStr = "";
    String prefix_1 = "", prefix_2 = "";
    String postfix_1 = "", postfix_2 = "";

    @Override
    public void onStart(){
       super.onStart();
       clearAll();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set which xml file to implement
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

        mode = (Button)findViewById(R.id.mode);

        display = (TextView) findViewById(R.id.display);

        //init variables
        displayContent_1 = "0";
        displayContent_2 = "";
        functionStr = "";
        prefix_1 = ""; prefix_2 = "";
        postfix_1 = ""; postfix_2 = " ";

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            displayContent_1 = bundle.getString("displayContent_1");
            displayContent_2 = bundle.getString("displayContent_2");
            prefix_1 = bundle.getString("prefix_1");
            prefix_2 = bundle.getString("prefix_2");
            postfix_1 = bundle.getString("postfix_1");
            postfix_2 = bundle.getString("postfix_2");
            subTotal = bundle.getDouble("subTotal");
        }

        postDisplay();




        mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,AdvActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Log.d("info-Main", "passing through mode listener");
                Log.d("info-Main", "displayContent_1= " + displayContent_1);

                intent.putExtra("displayContent_1", displayContent_1);
                intent.putExtra("displayContent_2", displayContent_2);
                intent.putExtra("prefix_1" ,prefix_1);
                intent.putExtra("prefix_2", prefix_2);
                intent.putExtra("postfix_1", postfix_1);
                intent.putExtra("postfix_2", postfix_2);
                intent.putExtra("subTotal", subTotal);

                startActivity(intent);
            }
        });

        buttonEquals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(functionStr.length() == 1) {
                    double sign1 = 1.0f, sign2 = 1.0f;
                    double dc1 = 0, dc2 = 0;

                    if (prefix_1.equals("-"))
                        sign1 = -1;
                    if (prefix_2.equals("-"))
                        sign2 = -1;
                    if(!displayContent_1.equals(""))
                        dc1 = sign1 * Double.valueOf(displayContent_1.trim());
                    if(!displayContent_2.equals(""))
                        dc2 = sign2 * Double.valueOf(displayContent_2.trim());

                    switch (functionStr) {
                        case "+":
                            subTotal = dc2 + dc1;
                        break;
                        case "-":
                            subTotal = dc2 - dc1;
                            break;
                        case "x":
                            subTotal = dc2 * dc1;
                            break;
                        case "/":
                            subTotal = dc2 / dc1;
                            break;
                        default:
                            break;
                    }

                    displayContent_1 = Double.toString(Math.abs(subTotal));
                    //trim trailing decimal if answer is integer
//                    if(displayContent_1.substring(displayContent_1.indexOf(".")).length() < 3)
//                        displayContent_1 = displayContent_1.substring(0, displayContent_1.indexOf("."));

                    if (subTotal < 0) {
                        prefix_1 = "-";
                    } else {
                        prefix_1 = "";
                    }

                    displayContent_2 = "";
                    functionStr = "";
                    prefix_2 = "";
                    postfix_1 = "";
                    postfix_2 = "";
                    subTotal = 0;
                    postDisplay();
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                clearAll();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(displayContent_1.length() > 1) {
                    displayContent_1 = displayContent_1.substring(0, displayContent_1.length() - 1);
                }else {
                    displayContent_1 = "0";
                    prefix_1 = ""; prefix_2 = "";
                }
                postDisplay();
            }
        });

        //*************** function keys *******************
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
            public void onClick(View view){ setFunction("x"); }
        });
        buttonDivide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setFunction("/"); }
        });


        // ********* number keys *************
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

    public void clearAll(){
        displayContent_1 = "0";
        displayContent_2 = "";
        functionStr = "";
        prefix_1 = ""; prefix_2 = "";
        postfix_1 = ""; postfix_2 = " ";
        subTotal = 0;
        postDisplay();
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
        //check for duplicate dots - ignore second dot
        //if length > 1 and first char == "0" then remove first char
        //check if negative
        //check for index of dot. -1 means no dot
        int dot1 = displayContent_1.indexOf('.');

        if(dot1 == -1) { //no dot in the display string
            if (str.equals(".")) { //if input is dot
                displayContent_1 = displayContent_1 + str;
            } else { //input is not a dot
                if(displayContent_1.startsWith("0")) { //suppress leading zero
                    displayContent_1 = displayContent_1.substring(1); //remove
                }
                displayContent_1 = displayContent_1 + str;
            }
        } else if(!str.equals(".")){ //there is a dot in the display string and not the input
            displayContent_1 = displayContent_1 + str;
        }

        postDisplay();
    }

    void setFunction(String str){
        //test for negative sign condition and entry display contains only a single zero
        if(displayContent_1.startsWith("0") && displayContent_1.length() == 1 && str.equals("-")){
            prefix_1 = "-";
        } else {
            functionStr = str;
            displayContent_2 = displayContent_1;
            displayContent_1 = "0";
            prefix_2 = prefix_1;
            prefix_1 = "";
        }
        postDisplay();
    }

   void postDisplay(){
       display.setText(prefix_2 + displayContent_2 + postfix_2 + functionStr
               + "\n" + prefix_1 + displayContent_1 + postfix_1);
   }


}



//for checking for duplicate dots
//int dot1 = input.indexOf('.');
//boolean hasTowDots = dot1 != -1 && input.indexOf('.', dot1+1) != -1;
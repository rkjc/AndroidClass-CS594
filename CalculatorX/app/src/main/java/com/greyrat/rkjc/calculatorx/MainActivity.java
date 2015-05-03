package com.greyrat.rkjc.calculatorx;

import java.text.DecimalFormat;
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
    private String prefix_1 = "", prefix_2 = "";
    private String postfix_1 = "", postfix_2 = "";
    private String sign_1 = "", sign_2 = "";
    private String textSize = "40dp";
    private boolean finished = false;
    private boolean needSecondOperand = false;


    @Override
    public void onStart(){
       super.onStart();
       //might need to use this
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
        sign_1 = ""; sign_2 = "";
        finished = false;
        textSize = "40dp";

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            displayContent_1 = bundle.getString("displayContent_1");
            displayContent_2 = bundle.getString("displayContent_2");
            prefix_1 = bundle.getString("prefix_1");
            prefix_2 = bundle.getString("prefix_2");
            sign_1 = bundle.getString("sign_1");
            sign_2 = bundle.getString("sign_2");
            postfix_1 = bundle.getString("postfix_1");
            postfix_2 = bundle.getString("postfix_2");
            functionStr = bundle.getString("functionStr");
            textSize = bundle.getString("textSize");
            subTotal = bundle.getDouble("subTotal");
            needSecondOperand = bundle.getBoolean("needSecondOperand");
            if(displayContent_1 == null){ //as happens when the app is restarted
                clearAll();
            }
        }

        postDisplay();

        //*************** action keys *******************
        mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switchMode();
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
                }else if(displayContent_1.equals("0") && displayContent_2.length() > 0) { //d_1 length == 1
                    functionStr = "";
                    //move back to one line being displayed
                    displayContent_1 = displayContent_2;
                    displayContent_2 = "";
                    prefix_1 = ""; postfix_1 = "";
                    prefix_2 = ""; postfix_2 = "";
                    sign_1 = sign_2;
                    sign_2 = "";
                } else { //d_1 length == 1 and d_1 != "0"
                    displayContent_1 = "0";
                    sign_1 = "";
                }
                postDisplay();
            }
        });

        buttonEquals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(functionStr.length() > 0) {
                    double sign1 = 1.0f, sign2 = 1.0f;
                    double dc1 = 0, dc2 = 0;

                    if (sign_1.equals("-"))
                        sign1 = -1;
                    if (sign_2.equals("-"))
                        sign2 = -1;
                    if(!displayContent_1.equals(""))
                        dc1 = sign1 * Double.valueOf(displayContent_1.trim());
                    if(!displayContent_2.equals(""))
                        dc2 = sign2 * Double.valueOf(displayContent_2.trim());

                    Log.d("info-MAIN", "buttonEquals.setOnClickListener= functionStr= " + functionStr);
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
                        case "sin":
                            subTotal = Math.cos(dc1);
                            break;
                        case "cos":
                            subTotal = Math.cos(dc1);
                            break;
                        case "tan":
                            subTotal = Math.tan(dc1);
                            break;
                        case "ln":
                            subTotal = Math.log(dc1);
                            break;
                        case "log":
                            subTotal = Math.log10(dc1);
                            break;
                        case "%":
                            subTotal = dc1 / 100;
                            break;
                        case "!":
                            int fact = 1; // this  will be the result
                            for (int i = 1; i <= (int)dc1; i++) {
                                fact *= i;
                            }
                            subTotal = (double)fact;
                            break;
                        case "sqrt":
                            subTotal = Math.sqrt(dc1);
                            break;
                        case "^":
                            subTotal = Math.pow(dc2, dc1);
                            break;
                        case "logx":
                            subTotal = (Math.log(dc2) / Math.log(dc1));
                            break;
                        case "log2":
                            subTotal = (Math.log(dc2) / Math.log(2));
                            break;
                        default:
                            break;
                    }

                    displayCalculation();
                }
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
        if (id == R.id.adv_mode) {
            switchMode();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //++++++++++++++++++++ function definitions  ++++++++++++++++
    public void clearAll(){
        displayContent_1 = "0";
        sign_1 = "";
        clearTop();
    }

    public void clearTop(){
        displayContent_2 = "";
        functionStr = "";
        prefix_1 = ""; prefix_2 = "";
        postfix_1 = ""; postfix_2 = "";
        sign_2 = "";
        textSize = "40dp";
        subTotal = 0;
        finished = true;

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
        //limit display length
        if(displayContent_1.length() < 14) {
            //check if a calculation has just ended and reset with next number key input
            if (finished) {
                clearAll();
                finished = false;
            }

            //check for duplicate dots - ignore second dot
            //check for index of dot. -1 means no dot
            int dot1 = displayContent_1.indexOf('.');

            if (dot1 == -1) { //no dot in the display string
                displayContent_1 = displayContent_1 + str;
            } else if (!str.equals(".")) { //there is a dot in the display string and not the input
                displayContent_1 = displayContent_1 + str;
            }
        }
        postDisplay();
    }

    void setFunction(String str){
        //test for negative sign condition and entry display contains only a single zero
        if(displayContent_1.startsWith("0") && displayContent_1.length() == 1 && str.equals("-")){
            sign_1 = "-";
            finished = false;
        } else if(functionStr.length() == 0){
            functionStr = str;
            postfix_2 = " " + str;
            displayContent_2 = displayContent_1;
            displayContent_1 = "0";
            prefix_2 = prefix_1;
            prefix_1 = "";
            sign_2 = sign_1;
            sign_1 = "";
            finished = false;
        } else {
            functionStr = str;
        }
        postDisplay();
    }

   void postDisplay(){
       //suppress leading zeros
       int dotLoc = displayContent_1.indexOf(".");
       if(dotLoc == 0) {
           displayContent_1 = "0" + displayContent_1;
       } else if( displayContent_1.length() > 1 && displayContent_1.startsWith("0") && (dotLoc > 1 || dotLoc < 0)){
           displayContent_1 = displayContent_1.substring(1);
       }

       //suppress trailing zeros
       //TODO

       display.setText(prefix_2 + sign_2 + displayContent_2 + postfix_2
               + "\n" + prefix_1 + sign_1 + displayContent_1 + postfix_1);
   }

    void displayCalculation(){
        if(subTotal > 99999999999999.0){
            clearAll();
            displayContent_1 = "ERROR";
            postDisplay();
        } else {

            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            // System.out.println(df.format(subTotal));

            displayContent_1 = df.format(Math.abs(subTotal));
            displayContent_1 = displayContent_1.substring(0, Math.min(displayContent_1.length(), 14));

            Log.d("info-Main", "displayContent_1= " + displayContent_1);
            //trim trailing decimal if answer is integer
//                    if(displayContent_1.substring(displayContent_1.indexOf(".")).length() < 3)
//                        displayContent_1 = displayContent_1.substring(0, displayContent_1.indexOf("."));

            if (subTotal < 0) {
                sign_1 = "-";
            } else {
                sign_1 = "";
            }

            clearTop();
            postDisplay();
        }
    }

    void switchMode(){  //to advance keyboard
        Intent intent = new Intent(MainActivity.this,AdvActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Log.d("info-MAIN", "mode.setOnClickListener displayContent_1= " + displayContent_1);

        intent.putExtra("displayContent_1", displayContent_1);
        intent.putExtra("displayContent_2", displayContent_2);
        intent.putExtra("prefix_1" ,prefix_1);
        intent.putExtra("prefix_2", prefix_2);
        intent.putExtra("sign_1" ,sign_1);
        intent.putExtra("sign_2", sign_2);
        intent.putExtra("postfix_1", postfix_1);
        intent.putExtra("postfix_2", postfix_2);
        intent.putExtra("functionStr", functionStr);
        intent.putExtra("textSize", textSize);
        intent.putExtra("subTotal", subTotal);
        intent.putExtra("needSecondOperand", needSecondOperand);

        startActivity(intent);
    }
}

//debug statements generic
//Log.d("info-Main", "displayContent_1= " + displayContent_1);
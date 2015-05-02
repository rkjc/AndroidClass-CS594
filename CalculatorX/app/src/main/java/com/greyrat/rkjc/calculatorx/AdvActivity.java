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


public class AdvActivity extends ActionBarActivity {
    //declare variables
    Random rnd = new Random();
    private Button buttonClear, mode, buttonDelete;
    private Button sinKey, cosKey, tanKey, button03;
    private Button lnKey, logKey, piKey, eKey;
    private Button percentKey, factorialKey, sqrtKey, powerKey;
    private Button logxKey, log2Key, button32, equalKey;

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
        Log.d("info-ADV", "- onStart -");
        super.onStart();
        //probably not needed in child activity
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("info-ADV", "onCreate for AdvActivity");

        //set which xml file to implement
        setContentView(R.layout.adv_activity);

        sinKey = (Button)findViewById(R.id.button00);
        cosKey = (Button)findViewById(R.id.button01);
        tanKey = (Button)findViewById(R.id.button02);
        button03 = (Button)findViewById(R.id.button03);
        lnKey = (Button)findViewById(R.id.button10);
        logKey = (Button)findViewById(R.id.button11);
        piKey = (Button)findViewById(R.id.button12);
        eKey = (Button)findViewById(R.id.button13);
        percentKey = (Button)findViewById(R.id.button20);
        factorialKey = (Button)findViewById(R.id.button21);
        sqrtKey = (Button)findViewById(R.id.button22);
        powerKey = (Button)findViewById(R.id.button23);
        logxKey = (Button)findViewById(R.id.button30);
        log2Key = (Button)findViewById(R.id.button31);
        button32 = (Button)findViewById(R.id.button32);
        equalKey = (Button)findViewById(R.id.button33);

        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
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
        needSecondOperand = false;
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

        Log.d("info-ADV", "onCreate= displayContent_1= " + displayContent_1);

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
                switchMode();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switchMode();
            }
        });

        equalKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                Log.d("info-ADV", "- equalKey.setOnClickListener -");
//                if(functionStr.length() == 1) {
//                    double sign1 = 1.0f, sign2 = 1.0f;
//                    double dc1 = 0, dc2 = 0;
//
//                    if (sign_1.equals("-"))
//                        sign1 = -1;
//                    if (sign_2.equals("-"))
//                        sign2 = -1;
//                    if(!displayContent_1.equals(""))
//                        dc1 = sign1 * Double.valueOf(displayContent_1.trim());
//                    if(!displayContent_2.equals(""))
//                        dc2 = sign2 * Double.valueOf(displayContent_2.trim());
//
//                    switch (functionStr) {
//                        case "sin":
//                            Log.d("info-ADV", "equalKey.  pressed sin key");
//                            break;
//                        case "cos":
//
//                            break;
//                        case "tan":
//
//                            break;
//                        case "03":
//
//                            break;
//                        case "ln":
//
//                            break;
//                        case "log":
//
//                            break;
//                        case "%":
//
//                            break;
//                        case "!":
//
//                            break;
//                        case "sqrt":
//
//                            break;
//                        case "^":
//                            needSecondOperand = true;
//                            break;
//                        case "logx":
//                            needSecondOperand = true;
//                            break;
//                        case "log2":
//
//                            break;
//                        default:
//                            break;
//                    }
//
//                    displayAdvCalculation();
//                }
            }
        });




        //*************** function keys *******************
        sinKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("sin"); }
        });
        cosKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("cos"); }
        });
        tanKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("tan"); }
        });
        button03.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("03"); }
        });
        lnKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("ln"); }
        });
        logKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("log"); }
        });
        percentKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("%"); }
        });
        factorialKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("!"); }
        });
        sqrtKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("sqrt"); }
        });
        powerKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setAdvFunction("^"); }
        });
        logxKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ setAdvFunction("logx"); }
        });
        log2Key.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("log2"); }
        });
        button32.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvFunction("32"); }
        });


        // *************** number keys ******************
        piKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvDisplay("3.14159264"); }
        });
        eKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){  setAdvDisplay("2.71828182"); }
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
        Log.d("info-ADV", "- clearAll -");
        displayContent_1 = "0";
        displayContent_2 = "";
        functionStr = "";
        prefix_1 = ""; prefix_2 = "";
        postfix_1 = ""; postfix_2 = " ";
        sign_1 = ""; sign_2 = "";
        subTotal = 0;
        needSecondOperand = false;
        textSize = "40dp";
        switchMode();
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

    void setAdvDisplay(String str){
        Log.d("info-ADV", "- setDisplay -");
        displayContent_1 = str;
        switchMode();
    }

    void setAdvFunction(String str){
        //test for negative sign condition and entry display contains only a single zero
        needSecondOperand = false;
        functionStr = str;
        //do special formating for display

        if(str.equals("^")){
            prefix_1 = "";
            postfix_1 = " ^x";
            needSecondOperand = true;
        } else if(str.equals("logx")){
            prefix_1 = "log(";
            postfix_1 = ") base x";
            needSecondOperand = true;
        } else {
            prefix_1 = str + "(";
            postfix_1 = ")";
        }

        textSize = "30dp";

        if(needSecondOperand) {
            prefix_2 = prefix_1;
            postfix_2 = postfix_1;
            sign_2 = sign_1;
            sign_1 = "";
            displayContent_2 = displayContent_1;
            displayContent_1 = "";
            prefix_1 = "x= ";
            postfix_1 = "";
        }

        switchMode();
        //postDisplay();
    }

    void postDisplay(){
        Log.d("info-ADV", "postDisplay= -------------");
        Log.d("info-ADV", "postDisplay= displayContent_1= " + displayContent_1 +" ;");
        Log.d("info-ADV", "postDisplay= displayContent_2= " + displayContent_2 +" ;");
        Log.d("info-ADV", "postDisplay= prefix_1= " + prefix_1 + " ;");
        Log.d("info-ADV", "postDisplay= prefix_2= " + prefix_2 + " ;");
        Log.d("info-ADV", "postDisplay= sign_1= " + sign_1 + " ;");
        Log.d("info-ADV", "postDisplay= sign_2= " + sign_2 + " ;");
        Log.d("info-ADV", "postDisplay= postfix_1= " + postfix_1 + " ;");
        Log.d("info-ADV", "postDisplay= postfix_2= " + postfix_2 + " ;");
        Log.d("info-ADV", "postDisplay= functionStr= " + functionStr + " ;");
        Log.d("info-ADV", "postDisplay= -------------");

        display.setText(prefix_2 + sign_2 + displayContent_2 + postfix_2
                + "\n" + prefix_1 + sign_1 + displayContent_1 + postfix_1);
        //return to basic view - maybe
    }

    void displayAdvCalculation(){
        switchMode();
    }

    void switchMode(){  //back to basic
        Intent intent = new Intent(AdvActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Log.d("info-ADV", "mode.setOnClickListener displayContent_1= " + displayContent_1);

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



//for checking for duplicate dots
//int dot1 = input.indexOf('.');
//boolean hasTowDots = dot1 != -1 && input.indexOf('.', dot1+1) != -1;
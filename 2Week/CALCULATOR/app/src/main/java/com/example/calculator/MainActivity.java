package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.DoubleBuffer;
import java.security.interfaces.DSAPublicKey;

public class MainActivity extends AppCompatActivity {
   TextView input;
   String str1="";
   Double result = null;
   String lastOperation = "";
   String output = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (TextView)findViewById(R.id.input);
    }

    protected void onSaveInstanceState(Bundle outState){
        outState.putString("OPERATION", lastOperation);
        if(result!=null){
            outState.putDouble("RESULT", result);
        }
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        result = savedInstanceState.getDouble("RESULT");
        input.setText(result.toString());
        input.setText(lastOperation);
    }


    public void onNumberClick(View v){
        input.setTextSize(40);
        if(lastOperation.equals("=") && result!=null || lastOperation.equals("%") && result!=null || lastOperation.equals("x²") && result!=null || lastOperation.equals("√") && result!=null ) {
            result = null;
        }
        if(lastOperation.equals("C")){
            lastOperation="";
            result = null;
        }

        Button btn = (Button)v;
        if(btn.getText().equals(".")){
            if(str1.contains(".") || str1.length()==0){
                str1+="";
            }
            else
                str1+=".";
        }
        else{
            if(btn.getText().equals("DEL")){
                if(str1.length()>0)
                    str1 = str1.substring(0,str1.length()-1);
            }
            else{
                if(str1.equals("0")){
                    str1="";
                    str1+=btn.getText().toString();
                }
                else{
                    str1+=btn.getText().toString();
                }
            }

        }

        input.setText(str1);

    }

    public void onOperationClick(View v){
        input.setTextSize(40);
        Button btn = (Button)v;
        String operation = btn.getText().toString();
        if(operation.equals("x²") || operation.equals("%") || operation.equals("√") || operation.equals("C")){
            if(result!=null){
                try{
                    performOperation(result,operation);
                }catch (NumberFormatException ex){
                    input.setText("");
                }
            }
            else {
                try{
                    performOperation(Double.parseDouble(str1),operation);
                }catch (NumberFormatException ex){
                    input.setText("");
                }
            }
        }
        if(!operation.equals("=") && !operation.equals("x²") && !operation.equals("%") && !operation.equals("√") && !operation.equals("C")){
            input.setText(operation);
        }

        if(str1.length()>0){
            str1 = str1.replace(',','.');
            try{
                performOperation(Double.parseDouble(str1),operation);
            }catch (NumberFormatException ex){
                input.setText("");
            }
        }

        lastOperation = operation;
    }


    public void performOperation(Double a, String operation){
        if(a==0.0 && lastOperation.equals("÷")){
            input.setTextSize(30);
            input.setText("Can't divide by 0");
            result = 0.0;
            lastOperation = "";
        }
        else{
            if(operation.equals("x²")){
                result = a*a;

                if(Math.round(result)==result){
                    output = String.format("%.0f", result);
                    input.setText(output.toString());
                }
                else{
                    output = String.format("%.5f", result);
                    input.setText(output.toString());
                }
            }
            else if(operation.equals("%")){
                result = a *0.01;
                if(Math.round(result)==result){
                    output = String.format("%.0f", result);
                    input.setText(output.toString());
                }
                else{
                    output = String.format("%.5f", result);
                    input.setText(output.toString());
                }
            }
            else if(operation.equals("√")){
                result = Math.sqrt(a);
                if(Math.round(result)==result){
                    output = String.format("%.0f", result);
                    input.setText(output.toString());
                }
                else{
                    output = String.format("%.5f", result);
                    input.setText(output.toString());
                }
            }
            else if(operation.equals("C")){
                result = 0.0;
                input.setText("");
            }
            else {
                if(result == null){
                    result = a;
                }
                else{
                    if(operation.equals("=")){
                        switch(lastOperation) {
                            case "+":
                                result += a;
                                break;
                            case "−":
                                result -= a;
                                break;
                            case "×":
                                result *= a;
                                break;
                            case "÷":
                                result /= a;
                                break;
                        }

                        if(Math.round(result)==result){
                            output = String.format("%.0f", result);
                            input.setText(output.toString());
                        }
                        else{
                            output = String.format("%.5f", result);
                            input.setText(output.toString());
                        }
                    }
                    else{
                        switch(lastOperation) {
                            case "+":
                                result += a;
                                break;
                            case "−":
                                result -= a;
                                break;
                            case "×":
                                result *= a;
                                break;
                            case "÷":
                                result /= a;
                                break;
                        }
                    }


                }
            }
        }




        str1="";
    }

}

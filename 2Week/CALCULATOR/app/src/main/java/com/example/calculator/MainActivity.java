package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewAutoScrollHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;

import java.nio.DoubleBuffer;
import java.security.interfaces.DSAPublicKey;

public class MainActivity extends AppCompatActivity {
   TextView input;
   String str1="";
   Double result = null;
   String lastOperation = "";
   String output = "";

   public boolean isNumeric(String strNum){
       if(strNum==null)
           return false;
       try{
           double d = Double.parseDouble(strNum);
       }catch (NumberFormatException e){
           return false;
       }
       return true;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (TextView)findViewById(R.id.input);
    }

    protected void onSaveInstanceState(Bundle outState){
       outState.putString("OPERATION", lastOperation);
       outState.putString("INPUT", input.getText().toString());
       if(result!=null){
           outState.putDouble("RESULT",result);
       }

       super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String temp = savedInstanceState.getString("INPUT");

        lastOperation = savedInstanceState.getString("OPERATION");
        if(isNumeric(temp)){
            if(lastOperation.equals("=") || lastOperation.equals("sin") || lastOperation.equals("cos") || lastOperation.equals("tan") || lastOperation.equals("ln") || lastOperation.equals("!") || lastOperation.equals("π") || lastOperation.equals("e")){
                result = Double.parseDouble(temp);
                input.setText(result.toString());
            }else{
                if(!lastOperation.isEmpty())
                    result = savedInstanceState.getDouble("RESULT");
                str1 = temp;
                input.setText(str1);
            }
        }
        else{
            result = savedInstanceState.getDouble("RESULT");
            if(!lastOperation.equals("=") && !lastOperation.equals("sin") && !lastOperation.equals("C") && !lastOperation.equals("cos") && !lastOperation.equals("tan") && !lastOperation.equals("ln") && !lastOperation.equals("!") && !lastOperation.equals("sqrt") && !lastOperation.equals("π") && !lastOperation.equals("e")) {
                input.setText(lastOperation);
            }
        }
    }


    public void onNumberClick(View v){
        input.setTextSize(40);
        if(lastOperation.equals("=") && result!=null ||  lastOperation.equals("x²") && result!=null || lastOperation.equals("sqrt") && result!=null ||
        lastOperation.equals("sin")  && result!=null || lastOperation.equals("cos") && result!=null || lastOperation.equals("tan")  && result!=null || lastOperation.equals("ln") && result!=null || lastOperation.equals("!") && result!=null || lastOperation.equals("π") && result!=null || lastOperation.equals("e") && result!=null) {
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
        if(operation.equals("x²") || operation.equals("sqrt") || operation.equals("C") || operation.equals("sin") || operation.equals("cos") || operation.equals("tan") || operation.equals("ln") || operation.equals("!") || operation.equals("π") || operation.equals("e")){
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

        if(!operation.equals("=") && !operation.equals("x²") && !operation.equals("sqrt") && !operation.equals("C") && !operation.equals("sin") && !operation.equals("cos") && !operation .equals("tan") && !operation.equals("ln") && !operation.equals("!") && !operation.equals("π") && !operation.equals("e")){
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
            else if(operation.equals("sqrt")){
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
            else if (operation.equals("!")){
                double temp = a;
                result = 1.0;
                for (int i = 2; i<=temp; i++){
                    result*=i;
                }
                input.setText(result.toString());
            }
            else if (operation.equals("sin")){
                double temp = Math.toRadians(a);
                result = Math.sin(temp);
                input.setText(result.toString());
            }
            else if (operation.equals("cos")){
                double temp = Math.toRadians(a);
                result = Math.cos(temp);
                input.setText(result.toString());
            }
            else if (operation.equals("tan")){
                double temp = Math.toRadians(a);
                result = Math.tan(temp);
                input.setText(result.toString());
            }
            else if(operation.equals("ln")){
                if(a==0.0){
                    input.setTextSize(30);
                    input.setText("Incorrect argument!");
                    result = 0.0;
                    lastOperation = "";
                }
                else{
                    result = Math.log(a);
                    input.setText(result.toString());
                }

            }
            else if(operation.equals("π")){
                result = a*Math.PI;
                input.setText(result.toString());
            }
            else if(operation.equals("e")){
                result = Math.exp(a);
                input.setText(result.toString());
            }
            else if(operation.equals("C")){
                result = 0.0;
                input.setText("");
            }


            else {
                boolean isOk = true;
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

                            case "%":
                                result = (result*a)/100;
                                break;
                            case "^":
                                result = Math.pow(result,a);
                                break;
                            case "√":
                                result = Math.pow(result,1.0/a);
                                break;
                            case "log":
                                if(result==0.0){
                                    isOk =false;
                                }
                                else
                                    result = Math.log(result)/Math.log(a);
                                break;
                        }
                            if(Math.round(result)==result){
                                if(!isOk && lastOperation.equals("log")){
                                    input.setTextSize(30);
                                    input.setText("Incorrect argument!");
                                    result = 0.0;
                                    lastOperation = "";
                                    isOk=true;
                                }
                                else{
                                    output = String.format("%.0f", result);
                                    input.setText(output.toString());
                                }

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
                            case "-":
                                result-=a;
                                break;
                            case "×":
                                result*=a;
                                break;

                            case "÷":
                                result /= a;
                                break;
                            case "%":
                                result = (result*a)/100;
                                break;
                            case "^":
                                result = Math.pow(result,a);
                                break;
                            case "√":
                                result = Math.pow(result,1.0/a);
                                break;
                            case "log":
                                if(result==0.0){
                                    isOk =false;
                                }
                                else
                                    result = Math.log(result)/Math.log(a);
                                break;
                        }
                    }


                }
            }
        }




        str1="";
    }

}

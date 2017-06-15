package com.tempconverter.danielmartinez.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;

import java.text.NumberFormat;


public class TempConverterActivity extends AppCompatActivity
implements OnEditorActionListener{

    //declare  widget variables
    private EditText fahrenheitET;
    private TextView fahrenheitlabel;
    private TextView celciusLabel;
    private TextView celciusTV;

    //instance variables
    private String fahrenheitAmountString = "";

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //reference widgets
        fahrenheitET = (EditText) findViewById(R.id.fahrenheitET);
        fahrenheitlabel = (TextView) findViewById(R.id.fahrenheit_label);
        celciusLabel = (TextView) findViewById(R.id.celciusLabel);
        celciusTV = (TextView) findViewById(R.id.celciusTV);

        //set Listeners
        fahrenheitET.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if( actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {

        fahrenheitAmountString = fahrenheitET.getText().toString();
        float fahrenheitNum;
        if(fahrenheitAmountString.equals(""))
        {
            fahrenheitNum = 0;
        }
        else
        {
            fahrenheitNum = Float.parseFloat(fahrenheitAmountString);
        }
        float celciusNum;
        celciusNum =((fahrenheitNum -32) * 5/9);

        celciusTV.setText(String.valueOf(celciusNum));
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitAmountString", fahrenheitAmountString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();

        fahrenheitAmountString = savedValues.getString("fahrenheitAmountString", "");
        fahrenheitET.setText(fahrenheitAmountString);
        calculateAndDisplay();
    }

}

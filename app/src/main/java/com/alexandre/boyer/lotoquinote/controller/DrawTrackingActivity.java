package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Util;

import static com.alexandre.boyer.lotoquinote.model.Util.stringToInt;

public class DrawTrackingActivity extends AppCompatActivity
{
    private Button mFinishButton;
    private NumberPicker mNumberPicker1;
    private NumberPicker mNumberPicker2;
    private EditText mNumberDrewText;
    private Number mNumber = new Number(0);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_tracking);

        mFinishButton = findViewById(R.id.activity_draw_finishButton);
        mNumberPicker1 = findViewById(R.id.activity_draw_picker1);
        mNumberPicker2 = findViewById(R.id.activity_draw_picker2);
        mNumberDrewText = findViewById(R.id.activity_draw_numberDrew);

        mFinishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mNumberPicker1.setMinValue(0);
        mNumberPicker1.setMaxValue(9);
        mNumberPicker2.setMinValue(0);
        mNumberPicker2.setMaxValue(9);

        mNumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                mNumber.setNumber((newVal*10) + mNumber.getNumber()%10);
                int numberSet = mNumber.getNumber();
                if(numberSet <= 90)
                {
                    mNumber.setNumber(numberSet);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
                else
                {
                    mNumber.setNumber(90);
                    mNumberPicker1.setValue(9);
                    mNumberPicker2.setValue(0);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
            }
        });

        mNumberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                mNumber.setNumber((mNumber.getNumber()/10)*10 + newVal);
                int numberSet = mNumber.getNumber();
                if(numberSet <= 90)
                {
                    mNumber.setNumber(numberSet);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
                else
                {
                    mNumber.setNumber(90);
                    mNumberPicker1.setValue(9);
                    mNumberPicker2.setValue(0);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
            }
        });

        mNumberDrewText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String ch = s.toString();
                int numberTextEdit = Util.stringToInt(ch);
                mNumber.setNumber(numberTextEdit);

                if(ch.length() == 1)
                {
                    mNumberPicker1.setValue(0);
                    mNumberPicker2.setValue(mNumber.getNumber());
                }
                else if (ch.length() == 2)
                {
                    mNumberPicker1.setValue(mNumber.getNumber()/10);
                    mNumberPicker2.setValue(mNumber.getNumber()%10);
                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }
}

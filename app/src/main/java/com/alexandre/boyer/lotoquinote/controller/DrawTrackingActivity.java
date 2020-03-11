package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Tirage;
import com.alexandre.boyer.lotoquinote.model.Util;

public class DrawTrackingActivity extends AppCompatActivity
{
    private Button mFinishButton;
    private NumberPicker mNumberPicker1;
    private NumberPicker mNumberPicker2;
    private EditText mNumberDrewText;
    private Number mNumber = new Number(1);
    private TextView mDrawTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_tracking);

        mFinishButton = findViewById(R.id.activity_draw_finishButton);
        mNumberPicker1 = findViewById(R.id.activity_draw_picker1);
        mNumberPicker2 = findViewById(R.id.activity_draw_picker2);
        mNumberDrewText = findViewById(R.id.activity_draw_numberDrew);
        mDrawTitle = findViewById(R.id.activity_draw_title);

        Intent intent = getIntent();
        if(intent != null)
        {
            mDrawTitle.setText(intent.getStringExtra("mDrawObject"));
        }



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

        mNumberPicker1.setValue(0);
        mNumberPicker2.setValue(mNumber.getNumber());

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
                mNumberDrewText.setCursorVisible(true);

                if(ch.length() == 1)
                {
                    if(numberTextEdit == 0)
                    {
                        mNumber.setNumber(1);
                        mNumberPicker1.setValue(0);
                        mNumberPicker2.setValue(1);
                        String newString = '1'+ch.substring(1);
                        mNumberDrewText.setText(newString);
                    }
                    else
                    {
                        mNumberPicker1.setValue(0);
                        mNumberPicker2.setValue(mNumber.getNumber());
                    }
                }
                else if (ch.length() == 2)
                {
                    if(numberTextEdit > 90)
                    {
                        mNumber.setNumber(90);
                        mNumberPicker1.setValue(9);
                        mNumberPicker2.setValue(0);
                        String newString = ch.substring(0,1)+'0'+ch.substring(2);
                        mNumberDrewText.setText(newString);
                    }
                    else
                    {
                        mNumberPicker1.setValue(mNumber.getNumber()/10);
                        mNumberPicker2.setValue(mNumber.getNumber()%10);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        mNumberDrewText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE) mNumberDrewText.clearFocus();
                return false;
            }
        });
    }
}

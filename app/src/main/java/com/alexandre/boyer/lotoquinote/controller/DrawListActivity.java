package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alexandre.boyer.lotoquinote.R;

public class DrawListActivity extends AppCompatActivity
{
    private Button mBackButton;
    private Button mHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_list);

        mBackButton = findViewById(R.id.drawListBackButton);
        mHomeButton = findViewById(R.id.drawListHomeButton);

        mBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mHomeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainActivity);
            }
        });
    }
}

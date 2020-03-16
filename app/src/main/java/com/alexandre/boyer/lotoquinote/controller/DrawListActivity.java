package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Tirage;
import com.alexandre.boyer.lotoquinote.model.Util;

import java.util.List;

public class DrawListActivity extends AppCompatActivity
{
    private Button mBackButton;
    private Button mHomeButton;
    private Tirage mDraw = new Tirage();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_list);

        mBackButton = findViewById(R.id.drawListBackButton);
        mHomeButton = findViewById(R.id.drawListHomeButton);

        GridView gv = (GridView) findViewById(R.id.drawListGridView);

        Intent intent = getIntent();
        if(intent != null)
        {
            Tirage originalDraw = (Tirage) intent.getSerializableExtra("drawList");
            assert originalDraw != null;
            mDraw.copy(originalDraw);

        }

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

        ArrayAdapter<Number> gridViewArrayAdapter = new ArrayAdapter<Number>(this, android.R.layout.simple_list_item_1, mDraw.getDraw());
        gv.setAdapter(gridViewArrayAdapter);
    }
}

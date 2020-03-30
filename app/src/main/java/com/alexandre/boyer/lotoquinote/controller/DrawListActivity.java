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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Tirage;
import com.alexandre.boyer.lotoquinote.model.Util;

import java.util.Collections;
import java.util.List;

public class DrawListActivity extends AppCompatActivity
{
    private Button mBackButton;
    private Button mHomeButton;
    private Spinner mSpinner;
    private Tirage mDraw = new Tirage();
    private boolean reversed = false; // pour savoir si la liste du tirage a été inversée ou non

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_list);

        mBackButton = findViewById(R.id.drawListBackButton);
        mHomeButton = findViewById(R.id.drawListHomeButton);
        mSpinner = findViewById(R.id.drawListSpinner);

        final GridView gv = (GridView) findViewById(R.id.drawListGridView);

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

        //ArrayAdapter<Number> gridViewArrayAdapter = new ArrayAdapter<Number>(this, android.R.layout.simple_list_item_1, mDraw.getDraw());
        //gv.setAdapter(gridViewArrayAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position)
                {
                    case 0:
                        if(!reversed)
                        {
                            Collections.reverse(mDraw.getDraw());
                            ArrayAdapter<Number> gridViewArrayAdapter = new ArrayAdapter<Number>(getApplicationContext(), android.R.layout.simple_list_item_1, mDraw.getDraw());
                            gv.setAdapter(gridViewArrayAdapter);
                            reversed = true;
                        }
                        break;
                    case 1:
                        System.out.println("Ordre croissant");
                        mDraw.ascendingSort(mDraw.getDraw());
                        ArrayAdapter<Number> gridViewArrayAdapter = new ArrayAdapter<Number>(getApplicationContext(), android.R.layout.simple_list_item_1, mDraw.getDraw());
                        gv.setAdapter(gridViewArrayAdapter);
                        break;
                    case 2:
                        System.out.println("Ordre décroissant");
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}

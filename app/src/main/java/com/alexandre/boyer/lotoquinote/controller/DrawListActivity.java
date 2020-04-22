package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Tirage;

public class DrawListActivity extends AppCompatActivity
{
    private Button mBackButton;
    private Spinner mSpinner;
    private Tirage mDraw = new Tirage();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_list);

        mBackButton = findViewById(R.id.drawListBackButton);
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
                        ArrayAdapter<Number> gridViewArrayAdapter = new ArrayAdapter<Number>(getApplicationContext(), android.R.layout.simple_list_item_1, mDraw.drawedSort());
                        gv.setAdapter(gridViewArrayAdapter);
                        break;
                    case 1:
                        System.out.println("Ordre croissant");
                        gridViewArrayAdapter = new ArrayAdapter<Number>(getApplicationContext(), android.R.layout.simple_list_item_1, mDraw.ascendingSort());
                        gv.setAdapter(gridViewArrayAdapter);
                        break;
                    case 2:
                        System.out.println("Ordre décroissant");
                        gridViewArrayAdapter = new ArrayAdapter<Number>(getApplicationContext(), android.R.layout.simple_list_item_1, mDraw.descendingSort());
                        gv.setAdapter(gridViewArrayAdapter);
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

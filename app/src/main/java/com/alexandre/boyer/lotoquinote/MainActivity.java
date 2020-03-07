package com.alexandre.boyer.lotoquinote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ListView mListView;
    Button mNewDrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mNewDrawButton = findViewById(R.id.newDraw);
        final List<Tirage> draws = new ArrayList<>();

        mNewDrawButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Date today = new Date();
                Tirage mDraw = new Tirage("Suivi du tirage n° ",today);
                draws.add(mDraw);

                ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
                HashMap<String,String> map;

                // Ajout d'un item dans la listItem
                for (int i=0; i<draws.size();i++){
                    map = new HashMap<String,String>();
                    map.put(draws.get(i).getTitle(),draws.get(i).getTitle());
                    listItem.add(map);

                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
                            listItem,R.layout.liste_tirages_items, new String[] {draws.get(i).getTitle()},
                            new int[] {R.id.activity_main_name_tirage_txt});
                    mListView.setAdapter(adapter);
                }
                // Compter le nombre de tirages pour savoir quel numéro de tirage doit être crée
            }
        });
    }
}

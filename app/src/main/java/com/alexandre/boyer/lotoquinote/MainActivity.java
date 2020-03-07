package com.alexandre.boyer.lotoquinote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ListView mListView;
    Button mNewDrawButton;

    /*
    private String[] tirages = new String[]
            {
                    "Suivi du tirage n°1","Suivi du tirage n°2","Suivi du tirage n°3",
                    "Suivi du tirage n°4","Suivi du tirage n°5","Suivi du tirage n°6",
                    "Suivi du tirage n°7","Suivi du tirage n°8","Suivi du tirage n°9",
                    "Suivi du tirage n°10","Suivi du tirage n°11","Suivi du tirage n°12",
                    "Suivi du tirage n°13","Suivi du tirage n°14","Suivi du tirage n°15"
            };

     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mNewDrawButton = findViewById(R.id.newDraw);

        /*
        mListView.setClickable(true);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_multiple_choice, tirages);
        mListView.setAdapter(adapter);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
         */

        mNewDrawButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Date today = new Date();
                Tirage mDraw = new Tirage("Suivi du tirage n° ",today);
                List<Tirage> draws = new ArrayList<>();
                draws.add(mDraw);

                //String title = mTirage.getTitle();
                //String date = mTirage.getDate();

                // Ajout d'un item dans la listView

                for(int i = 0; i < draws.size(); i++)
                {
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_multiple_choice, Collections.singletonList(draws.get(i).getTitle()));
                    mListView.setAdapter(adapter);
                }

                mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);



                // Compter le nombre de tirages pour savoir quel numéro de tirage doit être crée
            }
        });

    }
}

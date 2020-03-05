package com.alexandre.boyer.lotoquinote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    ListView mListView;
    private String[] tirages = new String[]
            {
                    "Suivi du tirage n°1","Suivi du tirage n°2","Suivi du tirage n°3",
                    "Suivi du tirage n°4","Suivi du tirage n°5","Suivi du tirage n°6",
                    "Suivi du tirage n°7","Suivi du tirage n°8","Suivi du tirage n°9",
                    "Suivi du tirage n°10","Suivi du tirage n°11","Suivi du tirage n°12",
                    "Suivi du tirage n°13","Suivi du tirage n°14","Suivi du tirage n°15"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mListView.setClickable(true);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_multiple_choice, tirages);

        mListView.setAdapter(adapter);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


    }
}

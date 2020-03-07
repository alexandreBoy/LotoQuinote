package com.alexandre.boyer.lotoquinote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

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

        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_multiple_choice, tirages);*/
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map;

        for (int i=0; i<tirages.length;i++){
            map = new HashMap<String,String>();
            map.put("nom_tirage",tirages[i]);
            listItem.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),listItem,R.layout.liste_tirages_items, new String[] {"nom_tirage"},new int[] {R.id.activity_main_name_tirage_txt});

        mListView.setAdapter(adapter);


    }
}

package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Tirage;
import com.alexandre.boyer.lotoquinote.model.TirageAdapter;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private ListView mListView;
    private Button mNewDrawButton;
    private CheckBox mDrawCheckbox;
    private ImageButton mDeleteButton;
    private ImageButton mEditButton;
    private TirageAdapter mTirageAdapter;
    private List<Tirage> draws = new ArrayList<>();
    private Context mContext = this;
    private boolean mMode; // Booléen indiquant le mode d'affichage de la liste : True --> Edition/Suppression, False --> Affichage simple

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mNewDrawButton = findViewById(R.id.newDraw);
        mDrawCheckbox = findViewById(R.id.activity_main_tirage_checkbox);
        mDeleteButton = findViewById(R.id.deleteButton);
        mEditButton = findViewById(R.id.editButton);

        //On cache les ImageButton mDeleteButton et mEditButton
        mDeleteButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.GONE);


        mTirageAdapter = new TirageAdapter( this, (ArrayList<Tirage>) draws);
        mListView.setAdapter(mTirageAdapter);

        mMode = false;

        mNewDrawButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNewDrawButton.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mNewDrawButton.setEnabled(true);
                            }
                        });
                    }
                }, 2000);

                Date today = new Date();
                Tirage mDraw = new Tirage("Suivi du tirage n° "+(draws.size()+1),today);
                draws.add(mDraw);
                Toast toast = Toast.makeText(getApplicationContext(),"" + mDraw.getTitle() + " crée", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,550);
                toast.show();

                mTirageAdapter = new TirageAdapter(mContext, (ArrayList<Tirage>) draws);
                mListView.setAdapter(mTirageAdapter);
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id){
               CheckBox cb = view.findViewById(R.id.activity_main_tirage_checkbox);
               cb.setChecked(!cb.isChecked());


               if(!mMode){
                   // Permet de lancer l'activité "DrawTackingActivity" qui affiche la vue de suivi de tirage
                   Intent drawTrackingActivity = new Intent(MainActivity.this, DrawTrackingActivity.class);
                   startActivity(drawTrackingActivity);
               }else{
                    Object o = cb.getTag();
                    if(cb.isChecked()){
                        if(o instanceof Tirage ){
                            Toast toast = Toast.makeText(mContext,((Tirage) o).getTitle() + " sélectionné",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,550);
                            toast.show();

                        }else{
                            Log.d("Erreur Item", "Ce n'est pas un tirage");
                        }
                    }
               }

           }
       });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                if(mMode){
                    mMode = false;
                    mDeleteButton.setVisibility(View.GONE);
                    mEditButton.setVisibility(View.GONE);
                }else{
                    mMode = true;
                    mDeleteButton.setVisibility(View.VISIBLE);
                    mEditButton.setVisibility(View.VISIBLE);
                    Log.d("TEST",mListView.getItemAtPosition(0).toString());
                    /*for(int i =0; i<draws.size();i++){
                        Log.d()
                    }*/
                }
                return false;
            }
        });
    }
}

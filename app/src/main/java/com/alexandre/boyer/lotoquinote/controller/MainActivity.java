package com.alexandre.boyer.lotoquinote.controller;

import androidx.annotation.Nullable;
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
    public static final int MODIFY_POPUP = 1;


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
                   Object o = cb.getTag();
                   Tirage mDraw = (Tirage) o;
                   Intent drawTrackingActivity = new Intent(MainActivity.this, DrawTrackingActivity.class);
                   //drawTrackingActivity.putExtra("mDrawObject", mDraw.getTitle());
                   drawTrackingActivity.putExtra("mDrawObject", mDraw);
                   // Permet de lancer l'activité "DrawTackingActivity" qui affiche la vue de suivi de tirage
                   startActivity(drawTrackingActivity);
               }else{
                    Object o = cb.getTag();
                    if(cb.isChecked()){
                        if(o instanceof Tirage ){
                            Toast toast = Toast.makeText(mContext,((Tirage) o).getTitle(),Toast.LENGTH_SHORT);
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
                /*if(mMode){
                    mMode = false;
                    mDeleteButton.setVisibility(View.GONE);
                    mEditButton.setVisibility(View.GONE);
                }else{
                    mMode = true;
                    mDeleteButton.setVisibility(View.VISIBLE);
                    mEditButton.setVisibility(View.VISIBLE);
                    Log.d("TEST",mListView.getItemAtPosition(0).toString());
                }*/

                //On récupère l'objet via le tag de la checkbox
                CheckBox cb = view.findViewById(R.id.activity_main_tirage_checkbox);
                cb.setChecked(!cb.isChecked());
                Object o = cb.getTag();
                Tirage mDraw = (Tirage) o;
                Intent modifyIntent = new Intent(MainActivity.this, ModifyPopUpActivity.class);
                modifyIntent.putExtra("currentDraw", mDraw);
                modifyIntent.putExtra("position",position);
                startActivityForResult(modifyIntent,MODIFY_POPUP);


                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==MODIFY_POPUP){
            if(data.hasExtra("deletedDraw") && data.getIntExtra("deletedDraw",1)==1){
                int posDraw = data.getIntExtra("position",-1);
                if(posDraw == -1){
                    Log.d("MODIFY : ","position incorrecte !");
                }else{
                    draws.remove(posDraw);
                    mTirageAdapter.notifyDataSetChanged();
                }

            }else{
                if(data.hasExtra("modifiedDraw")){
                    Tirage newDraw = (Tirage) data.getSerializableExtra("modifiedDraw");
                    int posDraw = data.getIntExtra("position",-1);
                    if(posDraw == -1){
                        Log.d("MODIFY : ","position incorrecte !");
                    }else{
                        draws.set(posDraw,newDraw);
                        mTirageAdapter.notifyDataSetChanged();
                        Log.d("MODIFY :","Modification sauvegardée !");

                    }
                }
            }
        }
    }
}

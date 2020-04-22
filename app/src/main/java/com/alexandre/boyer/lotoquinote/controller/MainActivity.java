package com.alexandre.boyer.lotoquinote.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    private ListView mListView;
    private Button mNewDrawButton;
    private CheckBox mDrawCheckbox;
    private TirageAdapter mTirageAdapter;
    private List<Tirage> draws = new ArrayList<>();
    private Context mContext = this;
    private boolean mMode; // Booléen indiquant le mode d'affichage de la liste : True --> Edition/Suppression, False --> Affichage simple
    public static final int MODIFY_POPUP = 1;
    public static final int DRAW_TRACKING = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mNewDrawButton = findViewById(R.id.newDraw);
        mDrawCheckbox = findViewById(R.id.activity_main_tirage_checkbox);

        // Création de la liste des tirages
        mTirageAdapter = new TirageAdapter(this, (ArrayList<Tirage>) draws);
        mListView.setAdapter(mTirageAdapter);

        mMode = false; // affichage simple (pas en mode édition)

        //Chargement des tirages
        loadData();

        mNewDrawButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mNewDrawButton.setEnabled(false);

                // Timer de 2s après appui sur le bouton qui permet d'ajouter un tirage
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                mNewDrawButton.setEnabled(true);
                            }
                        });
                    }
                }, 2000);

                // Création du tirage
                Date today = new Date();
                Tirage mDraw = new Tirage("Suivi du tirage n° " + (draws.size() + 1), today);
                draws.add(mDraw);
                Toast toast = Toast.makeText(getApplicationContext(), "" + mDraw.getTitle() + " crée", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 550);
                toast.show();

                mTirageAdapter = new TirageAdapter(mContext, (ArrayList<Tirage>) draws);
                mListView.setAdapter(mTirageAdapter);
                saveData();
            }
        });

        // Gestion de la sélection d'un ou plusieurs tirages via les checkbox
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CheckBox cb = view.findViewById(R.id.activity_main_tirage_checkbox);
                cb.setChecked(!cb.isChecked());


                if (!mMode){
                    Object o = cb.getTag();
                    Tirage mDraw = (Tirage) o;
                    Intent drawTrackingActivity = new Intent(MainActivity.this, DrawTrackingActivity.class);
                    //On ajoute ici à l'intent, l'objet Tirage à modifier, ainsi que sa position dans la liste de tirages
                    drawTrackingActivity.putExtra("mDrawObject", mDraw);
                    drawTrackingActivity.putExtra("position", position);
                    startActivityForResult(drawTrackingActivity,DRAW_TRACKING);
                } else{
                    Object o = cb.getTag();
                    if (cb.isChecked()){
                        if (o instanceof Tirage){
                            Toast toast = Toast.makeText(mContext, ((Tirage) o).getTitle(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 550);
                            toast.show();

                        } else{
                            Log.d("Erreur Item", "Ce n'est pas un tirage");
                        }
                    }
                }

            }
        });

        // Gestion de l'appui long sur un item de la liste :
        // Lors d'un appui long l'activité ModifyPopUpActivity est lancée pour permettre l'édition du tirage sélectionné
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){

                //On récupère l'objet via le tag de la checkbox
                CheckBox cb = view.findViewById(R.id.activity_main_tirage_checkbox);
                cb.setChecked(!cb.isChecked());
                Object o = cb.getTag();
                Tirage mDraw = (Tirage) o;
                Intent modifyIntent = new Intent(MainActivity.this, ModifyPopUpActivity.class);
                modifyIntent.putExtra("currentDraw", mDraw);
                modifyIntent.putExtra("position", position);
                startActivityForResult(modifyIntent, MODIFY_POPUP);


                return false;
            }
        });


    }

    // Gestion de la persistance des données
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MODIFY_POPUP){
            if (data.hasExtra("deletedDraw") && data.getIntExtra("deletedDraw", 1) == 1){
                int posDraw = data.getIntExtra("position", -1);
                if (posDraw == -1){
                    Log.d("MODIFY : ", "position incorrecte !");
                } else{
                    draws.remove(posDraw);
                    mTirageAdapter.notifyDataSetChanged();
                }
                saveData();

            } else{
                if (data.hasExtra("modifiedDraw")){
                    Tirage newDraw = (Tirage) data.getSerializableExtra("modifiedDraw");
                    int posDraw = data.getIntExtra("position", -1);
                    if (posDraw == -1){
                        Log.d("MODIFY : ", "position incorrecte !");
                    } else{
                        draws.set(posDraw, newDraw);
                        mTirageAdapter.notifyDataSetChanged();
                        Log.d("MODIFY :", "Modification sauvegardée !");

                    }
                    saveData();
                }
            }
        }
        else{
            if(requestCode == DRAW_TRACKING){
                if(data.hasExtra("modifiedNumbers")){
                    Tirage newDraw = (Tirage) data.getSerializableExtra("modifiedNumbers");
                    int posDraw = data.getIntExtra("position", -1);
                    if (posDraw == -1){
                        Log.d("MODIFY : ", "position incorrecte !");
                    } else{
                        draws.set(posDraw, newDraw);
                        mTirageAdapter.notifyDataSetChanged();
                    }
                    saveData();

                }
            }
        }
    }

    private void saveData(){
        //Sauvegarde de la liste des tirages
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(draws);
        editor.putString("draws list",json);
        editor.apply();
        Log.d("Coucou","SaveData est lance !");
    }

    private void loadData(){
        //Chargement de la liste des tirages
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("draws list", null);
        Type type = new TypeToken<List<Tirage>>() {}.getType();
        draws = gson.fromJson(json,type);

        if(draws == null){
            draws = new ArrayList<>();
        }else{
            mTirageAdapter = new TirageAdapter(mContext, (ArrayList<Tirage>) draws);
            mListView.setAdapter(mTirageAdapter);
        }

        Log.d("Coucou","LoadData est lance !");


    }
}

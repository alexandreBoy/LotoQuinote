package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Tirage;

import java.util.Date;

public class ModifyPopUpActivity extends AppCompatActivity{

    private EditText mDrawNameEdtTxt;
    private TextView mDrawDateTxt;
    private Button mCancelBtn;
    private Button mDeleteBtn;
    private Button mValidateBtn;
    public static final int MODIFY_POPUP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pop_up);


        Intent drawToModify = getIntent();
        final Tirage currentDraw = (Tirage) drawToModify.getSerializableExtra("currentDraw");
        final int positionDraw = drawToModify.getIntExtra("position",-1);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8),(int) (height*0.6));

        //On récupère les éléments graphiques :

        mDrawNameEdtTxt = findViewById(R.id.activity_modify_draw_name_txt);
        mDrawDateTxt = findViewById(R.id.activity_modify_draw_date_txt);
        mDeleteBtn = findViewById(R.id.activity_modify_delete_btn);
        mCancelBtn = findViewById(R.id.activity_modify_cancel_btn);
        mValidateBtn = findViewById(R.id.activity_modify_validate_btn);

        //On met à jour les éléments graphiques avec les éléments du tirage sélectionné

        mDrawNameEdtTxt.setText(currentDraw.getTitle());
        mDrawDateTxt.setText(currentDraw.getDate());

        //On conserve le titre d'origine si les changements sont annulés :
        final String oldDrawTitle = currentDraw.getTitle();

        //On prépare ici l'intent à renvoyer;
        final Intent modifiedDraw = new Intent();

        //On règle ensuite un listener pour permettre la modification ou l'édition du tirage

        mDrawNameEdtTxt.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                String newDrawTitle = s.toString();
                currentDraw.setTitle(newDrawTitle);
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentDraw.setTitle(oldDrawTitle);
                modifiedDraw.putExtra("canceledDraw",currentDraw);
                setResult(MODIFY_POPUP,modifiedDraw);
                finish();
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                modifiedDraw.putExtra("deletedDraw",1);
                modifiedDraw.putExtra("position",positionDraw);
                setResult(MODIFY_POPUP,modifiedDraw);
                finish();
            }
        });

        mValidateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentDraw.setDate(new Date());
                modifiedDraw.putExtra("modifiedDraw",currentDraw);
                modifiedDraw.putExtra("position",positionDraw);
                setResult(MODIFY_POPUP,modifiedDraw);
                finish();
            }
        });




    }
}

package com.alexandre.boyer.lotoquinote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.alexandre.boyer.lotoquinote.R;
import com.alexandre.boyer.lotoquinote.model.Number;
import com.alexandre.boyer.lotoquinote.model.Tirage;
import com.alexandre.boyer.lotoquinote.model.Util;

public class DrawTrackingActivity extends AppCompatActivity
{
    private Button mFinishButton;
    private Button mAddButton;
    private Button mPos1List;
    private Button mPos2List;
    private Button mPos3List;
    private Button mPos4List;
    private Button mPos5List;
    private ImageButton mPos6List;
    private ImageButton mDeleteNumberPos1;
    private NumberPicker mNumberPicker1;
    private NumberPicker mNumberPicker2;
    private EditText mNumberDrewText;
    private Number mNumber = new Number(1);
    private TextView mDrawTitle;
    private Tirage mDraw = new Tirage();
    public static final int DRAW_TRACKING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_tracking);

        mFinishButton = findViewById(R.id.activity_draw_finishButton);
        mNumberPicker1 = findViewById(R.id.activity_draw_picker1);
        mNumberPicker2 = findViewById(R.id.activity_draw_picker2);
        mNumberDrewText = findViewById(R.id.activity_draw_numberDrew);
        mDrawTitle = findViewById(R.id.activity_draw_title);
        mAddButton = findViewById(R.id.activity_draw_addButton);
        mPos1List = findViewById(R.id.activity_draw_pos1List);
        mPos2List = findViewById(R.id.activity_draw_pos2List);
        mPos3List = findViewById(R.id.activity_draw_pos3List);
        mPos4List = findViewById(R.id.activity_draw_pos4List);
        mPos5List = findViewById(R.id.activity_draw_pos5List);
        mPos6List = findViewById(R.id.activity_draw_pos6List);
        mDeleteNumberPos1 = findViewById(R.id.activity_draw_deleteNumberPos1);

        //On prépare ici l'intent à renvoyer;
        final Intent modifiedNumbers = new Intent();


        Intent intent = getIntent();
        if(intent != null)
        {
            //mDrawTitle.setText(intent.getStringExtra("mDrawObject"));
            Tirage originalDraw = (Tirage) intent.getSerializableExtra("mDrawObject");
            assert originalDraw != null;
            //Ici, on ne fait pas de mDraw = draw, car l'objet ne sera pas dupliqué
            //On utilisera la méthode copy() de la classe Tirage qui permet de faire cela
            mDraw.copy(originalDraw);
            mDrawTitle.setText(mDraw.getTitle());
        }

        //Cette déclaration est en dehors du if car même si l'intent est null, la variable positionDraw prendra la valeur -1 signalant l'erreur
        final int positionDraw = intent.getIntExtra("position",-1);

        //Permet d'afficher les derniers nombres tirés si le tirage a déjà été entamé
        refreshView();


        // Sauvegarde des données lors de l'appui sur le bouton "Terminer" et fin de l'activité courante pour revenir à l'accueil de l'application
        mFinishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                modifiedNumbers.putExtra("modifiedNumbers",mDraw);
                modifiedNumbers.putExtra("position", positionDraw);
                setResult(DRAW_TRACKING,modifiedNumbers);
                finish();
            }
        });

        // Définition des valeurs minimales et maximales des NumberPicker
        mNumberPicker1.setMinValue(0);
        mNumberPicker1.setMaxValue(9);
        mNumberPicker2.setMinValue(0);
        mNumberPicker2.setMaxValue(9);

        mNumberPicker1.setValue(0);
        mNumberPicker2.setValue(mNumber.getNumber());

        // Gestion de la sélection des nombres dans les NumberPicker
        mNumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                mNumber.setNumber((newVal*10) + mNumber.getNumber()%10);
                int numberSet = mNumber.getNumber();
                if(numberSet <= 90)
                {
                    mNumber.setNumber(numberSet);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
                else
                    // Un numéro du lotoquine ne peut pas être supérieur à 90,
                    // on gère donc ce cas en "bloquant"
                    // les NumberPicker pour qu'ils donnent tous le temps la valeur 90
                    // quand la valeur choisit par l'utilisateur est supérieure à 90
                {
                    mNumber.setNumber(90);
                    mNumberPicker1.setValue(9);
                    mNumberPicker2.setValue(0);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
            }
        });

        mNumberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                mNumber.setNumber((mNumber.getNumber()/10)*10 + newVal);
                int numberSet = mNumber.getNumber();
                if(numberSet <= 90)
                {
                    mNumber.setNumber(numberSet);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
                else
                    // Même principe que précedemment
                {
                    mNumber.setNumber(90);
                    mNumberPicker1.setValue(9);
                    mNumberPicker2.setValue(0);
                    mNumberDrewText.setText(String.valueOf(mNumber.getNumber()));
                }
            }
        });


        mNumberDrewText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            // Adaptation des nombres affichés dans les NumberPicker en fonction du nombre entré dans le EditText
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String ch = s.toString();
                int numberTextEdit = Util.stringToInt(ch);
                mNumber.setNumber(numberTextEdit);
                mNumberDrewText.setCursorVisible(true);

                if(ch.length() == 1)
                {
                    if(numberTextEdit == 0)
                    {
                        mNumber.setNumber(1);
                        mNumberPicker1.setValue(0);
                        mNumberPicker2.setValue(1);
                        String newString = '1'+ch.substring(1);
                        mNumberDrewText.setText(newString);
                    }
                    else
                    {
                        mNumberPicker1.setValue(0);
                        mNumberPicker2.setValue(mNumber.getNumber());
                    }
                }
                else if (ch.length() == 2)
                {
                    if(numberTextEdit > 90)
                    {
                        mNumber.setNumber(90);
                        mNumberPicker1.setValue(9);
                        mNumberPicker2.setValue(0);
                        String newString = ch.substring(0,1)+'0'+ch.substring(2);
                        mNumberDrewText.setText(newString);
                    }
                    else
                    {
                        mNumberPicker1.setValue(mNumber.getNumber()/10);
                        mNumberPicker2.setValue(mNumber.getNumber()%10);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        // Gestion de l'ajout du nombre écrit dans le EditText lors du clique sur le bouton "ACTION_DONE" du clavier virtuel
        mNumberDrewText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    mNumberDrewText.clearFocus();
                    mAddButton.performClick();
                }
                return false;
            }
        });

        // Gestion de l'ajout des nombres à la liste de tirage
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mDraw.isNumberDrawn(mNumber)){ // si le nombre a déjà été tiré, cela est signalé par un toast
                    Toast toast = Toast.makeText(getApplicationContext(),"Le nombre "+mNumber.toString()+" a déjà été tiré", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,450);
                    toast.show();
                }else{ // sinon on ajoute le nombre à la liste
                    mDraw.addNumber(mNumber);
                    switch(mDraw.getDraw().size()) // permet d'afficher les 5 derniers nombres
                            // tirés dans les 5 cases situées en bas de cette activitée
                            // le dernier nombre tiré est celui le plus à gauche
                    {
                        case 0:
                            break;
                        case 1:
                            mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                            break;
                        case 2:
                            mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                            mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                            break;
                        case 3:
                            mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                            mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                            mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                            break;
                        case 4:
                            mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                            mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                            mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                            mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                            break;
                        default:
                            mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                            mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                            mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                            mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                            mPos5List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-5).toString());
                            break;
                    }


                    Toast toast = Toast.makeText(getApplicationContext(),"Numéro " + mNumber.getNumber() + " ajouté à la liste", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,450);
                    toast.show();

                    //On crée ensuite une nouvelle instance de l'objet Number
                    mNumber = new Number(mNumberPicker1.getValue()*10 + mNumberPicker2.getValue());

                }
            }
        });

        // Quand on clique sur la petite croix en dessous du dernier numéro tiré, on supprime ce numéro
        mDeleteNumberPos1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int deletedNumber = mDraw.getNumberAt(mDraw.getDraw().size()-1).getNumber();

                mDraw.deleteNumber();
                Toast toast;
                if(deletedNumber >=0){
                    toast = Toast.makeText(getApplicationContext(),"Numéro " + deletedNumber + " supprimé de la liste", Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(getApplicationContext(),"Aucun numéro supprimé", Toast.LENGTH_SHORT);
                }
                toast.setGravity(Gravity.CENTER_VERTICAL,0,450);
                toast.show();
                switch(mDraw.getDraw().size()) // On décale ensuite les numéros
                {
                    case 0:
                        mPos1List.setText("");
                        toast = Toast.makeText(getApplicationContext(),"Il n'y a pas de numéros pour ce tirage.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,450);
                        toast.show();
                        break;
                    case 1:
                        mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                        mPos2List.setText("");
                        break;
                    case 2:
                        mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                        mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                        mPos3List.setText("");
                        break;
                    case 3:
                        mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                        mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                        mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                        mPos4List.setText("");
                        break;
                    case 4:
                        mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                        mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                        mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                        mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                        mPos5List.setText("");
                        break;
                    default:
                        mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                        mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                        mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                        mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                        mPos5List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-5).toString());
                        break;
                }
            }
        });

        // Quand on clique sur le "+" : passage à l'activité "DrawListActivity"
        mPos6List.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent drawListActivity = new Intent(DrawTrackingActivity.this, DrawListActivity.class);
                drawListActivity.putExtra("drawList", mDraw);
                startActivity(drawListActivity);
            }
        });

    }



    // Permet de réafficher les 5 derniers numéros tirés si on quitte l'application et qu'on la relance
    private void refreshView(){
        switch(mDraw.getDraw().size())
        {
            case 0:
                break;
            case 1:
                mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                break;
            case 2:
                mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                break;
            case 3:
                mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                break;
            case 4:
                mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                break;
            default:
                mPos1List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-1).toString());
                mPos2List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-2).toString());
                mPos3List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-3).toString());
                mPos4List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-4).toString());
                mPos5List.setText(mDraw.getNumberAt(mDraw.getDraw().size()-5).toString());
                break;
        }
    }

    //Ici, on redéfinit la méthode appelé lorsque le bouton "Précédent" est appuyé. On renvoie ici un intent,
    //car l'activité MainActivity attends un resultat
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        Intent i = new Intent();
        setResult(DRAW_TRACKING,i);
        finish();
    }


}

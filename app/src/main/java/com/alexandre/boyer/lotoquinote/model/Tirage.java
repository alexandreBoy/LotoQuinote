package com.alexandre.boyer.lotoquinote.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Alexandre BOYER on 07/03/2020.
 */
public class Tirage implements Serializable
{
    String title;
    String date;
    Date today = new Date();
    ArrayList <Number> draw;

    public Tirage(){
        this.title = "Untitled";
        this.today = today;
        this.draw = new ArrayList<>();
    }


    public Tirage(String title, Date today)
    {
        this.title = title;
        this.today = today;
        this.draw = new ArrayList<>();
    }

    //Méthode qui permet de copier un tirage
    public void copy(Tirage otherDraw){
        this.setTitle(otherDraw.getTitle());
        this.setDate(new Date());
        this.setDraw(otherDraw.getDraw());
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy ' 'H:m");
        date = formatter.format(today);
        return date;
    }

    public void setDate(Date today)
    {
        this.today = new Date();
    }

    public ArrayList<Number> getDraw(){
        return draw;
    }

    public void setDraw(ArrayList<Number> draw){
        this.draw = draw;
    }

    // Ajouter un nombre à la liste
    public void addNumber(Number n)
    {
        draw.add(n);
    }

    // Supprimer le dernier nombre ajouté à la liste
    public void deleteNumber()
    {
        if(draw.size() > 0)
            draw.remove(draw.size() - 1);
    }

    // Récupérer un nombre situé à un index i
    public Number getNumberAt(int i)
    {
        Number n = new Number(-1);
        if(i>=0)
            n = draw.get(i);
        return n;
    }

    public boolean isNumberDrawn(Number n){
        boolean res = false;
        for(Number nb : draw){
            if(n.getNumber()==nb.getNumber()) res = true;
        }
        return res;
    }

    public ArrayList<Number> drawedSort(){
        //On créer la ArrayList à retourner
        ArrayList<Number> sorted_draw = new ArrayList<>();
        //On copie la ArrayList draw dans la nouvelle ArrayList
        for(Number nb : draw){
            sorted_draw.add(nb);
        }

        //On trie la ArrayList à retourner
        Collections.reverse(sorted_draw);
        //On retourne ensuite le tirage trié*/
        return sorted_draw;
    }

    public ArrayList<Number> ascendingSort(){
        //On créer la ArrayList à retourner
        ArrayList<Number> sorted_draw = new ArrayList<>();
        //On copie la ArrayList draw dans la nouvelle ArrayList
        for(Number nb : draw){
            sorted_draw.add(nb);
        }

        //On trie la ArrayList à retourner
        Collections.sort(sorted_draw);
        //On retourne ensuite le tirage trié*/
        return sorted_draw;
    }

    public ArrayList<Number> descendingSort(){
        //On créer la ArrayList à retourner
        ArrayList<Number> sorted_draw = new ArrayList<>();
        //On copie la ArrayList draw dans la nouvelle ArrayList
        for(Number nb : draw){
            sorted_draw.add(nb);
        }

        //On trie la ArrayList à retourner
        Collections.sort(sorted_draw,Collections.<Number>reverseOrder());
        //On retourne ensuite le tirage trié*/
        return sorted_draw;
    }


}

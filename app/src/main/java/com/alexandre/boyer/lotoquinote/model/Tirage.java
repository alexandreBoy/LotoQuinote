package com.alexandre.boyer.lotoquinote.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexandre BOYER on 07/03/2020.
 */
public class Tirage
{
    String title;
    String date;
    Date today = new Date();
    boolean isSelected;


    public Tirage(String title, Date today)
    {
        this.title = title;
        this.today = today;
        isSelected = false;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        title = title;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        date = formatter.format(today);
        return date;
    }

    public void setDate(Date today)
    {
        this.today = new Date();
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }
}

package com.alexandre.boyer.lotoquinote;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexandre BOYER on 07/03/2020.
 */
public class Tirage
{
    String Title;
    String date;
    Date today = new Date();


    public Tirage(String title, Date today)
    {
        Title = title;
        this.today = today;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
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
}

package com.alexandre.boyer.lotoquinote.model;

import java.io.Serializable;

/**
 * Created by Alexandre BOYER on 09/03/2020.
 */
public class Number implements Serializable,Comparable<Number>
{
    private int number;

    public Number(int number) { this.number = number; }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String toString(){
        return Integer.toString(this.number);
    }

    @Override
    public int compareTo(Number o){
        return(this.number - o.number);
    }
}

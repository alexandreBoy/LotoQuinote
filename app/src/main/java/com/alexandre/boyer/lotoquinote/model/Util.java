package com.alexandre.boyer.lotoquinote.model;

/**
 * Created by Alexandre BOYER on 09/03/2020.
 */
public class Util
{
    public static int stringToInt(String ch)
    {
        int rep = 0; // va contenir la réponse

        for(int i = 0; i<ch.length(); i++)
        {
            char c = ch.charAt(i);
            if(c>='0' && c<='9') // c € ['0', '9']
            {
                rep = rep * 10 + (c-'0');

            }
            else return(-1); // ch ne représente pas un entier
        }
        return(rep);
    }
}

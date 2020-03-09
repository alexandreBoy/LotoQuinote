package com.alexandre.boyer.lotoquinote.model;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexandre.boyer.lotoquinote.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TirageAdapter extends ArrayAdapter<Tirage>{

    private Context mContext;
    private List<Tirage> drawsList = new ArrayList<>();

    public TirageAdapter(@NonNull Context context, ArrayList<Tirage> list){
        super(context,0 , list);
        mContext = context;
        drawsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.liste_tirages_items, null, true);

            Tirage currentDraw = drawsList.get(position);

            TextView drawname = listItem.findViewById(R.id.activity_main_name_tirage_txt);
            drawname.setText(currentDraw.getTitle());
        }
        return listItem;
        }
    }


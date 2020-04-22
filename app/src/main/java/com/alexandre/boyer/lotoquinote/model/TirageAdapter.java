package com.alexandre.boyer.lotoquinote.model;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexandre.boyer.lotoquinote.R;

import java.util.ArrayList;
import java.util.List;

/*
    Cette classe permet de gérer la liste des tirages :
    elle prend ainsi un ArrayList d'objet Tirage en paramètre
*/

public class TirageAdapter extends ArrayAdapter<Tirage>{

    private Context mContext;
    private List<Tirage> drawsList = new ArrayList<>();
    private SparseBooleanArray mCheckStates;

    public TirageAdapter(@NonNull Context context, ArrayList<Tirage> list){
        super(context,0 , list);
        mContext = context;
        drawsList = list;
        mCheckStates = new SparseBooleanArray(drawsList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return drawsList.size();
    }

    @Override
    public Tirage getItem(int position) {
        return drawsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.liste_tirages_items,null,true);
            holder.checkBox = convertView.findViewById(R.id.activity_main_tirage_checkbox);
            holder.drawName = convertView.findViewById(R.id.activity_main_name_tirage_txt);
            convertView.setTag(holder);
        }else{
            // La méthode getTag renvoie l'objet ViewHolder mis en tag à la vue
            holder = (ViewHolder) convertView.getTag();
        }

        Tirage currentDraw = drawsList.get(position);
        holder.drawName.setText(currentDraw.getTitle());
        holder.checkBox.setTag(currentDraw);// à supprimer, à remplacer par : holder.drawName.setTag(currentDraw);
        holder.checkBox.setVisibility(View.GONE);
        holder.checkBox.setChecked(mCheckStates.get(position,false));
        return convertView;
        }


        public boolean isChecked(int position) {
            return mCheckStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);

        }

        public void toggle(int position) {
            setChecked(position, !isChecked(position));

        }
    }




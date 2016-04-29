package com.example.myprojectv_002.ResourceAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myprojectv_002.R;

public class RecyclerAdapter__empty extends RecyclerView.Adapter<RecyclerAdapter__empty.ViewHolder> {

    String text_empty;
    public RecyclerAdapter__empty(String text){text_empty=text;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_cardview,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_empty.setText(text_empty);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_empty;
        public Context context;

        public ViewHolder(View v){
            super (v);
            tv_empty=(TextView)v.findViewById(R.id.text_emtpy);
            context=v.getContext();
        }
    }
}

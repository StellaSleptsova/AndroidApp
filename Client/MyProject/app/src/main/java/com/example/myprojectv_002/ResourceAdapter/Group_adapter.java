package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.Group_item;

import java.util.List;

public class Group_adapter extends ArrayAdapter<Group_item> {
    Context context;
    int resLayout;
    List<Group_item> listItems;

    public Group_adapter(Context context, int resLayout, List<Group_item> listItems) {
        super(context, resLayout, listItems);

        this.context=context;
        this.resLayout=resLayout;
        this.listItems=listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.text_title);
        TextView cS=(TextView)v.findViewById(R.id.text_subtitle_students);
        TextView cP=(TextView)v.findViewById(R.id.text_subtitle_problems);

        Group_item navItem = listItems.get(position);

        tvTitle.setText(navItem.getTitle());
        cS.setText(navItem.getCountStudent());
        cP.setText(navItem.getCountProblems());

        return v;
    }
}

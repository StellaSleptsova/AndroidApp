package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.GroupProblems_item;

import java.util.List;

public class GroupProblems_adapter extends ArrayAdapter<GroupProblems_item> {
    Context context;
    int resLayout;
    List<GroupProblems_item> listItems;

    public GroupProblems_adapter(Context context, int resLayout, List<GroupProblems_item> listItems) {
        super(context, resLayout, listItems);
        this.context=context;
        this.resLayout=resLayout;
        this.listItems=listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.text_title_group);
        TextView cP=(TextView)v.findViewById(R.id.text_subtitle_problems_main);

        GroupProblems_item navItem = listItems.get(position);

        tvTitle.setText(navItem.getTitle());
        cP.setText(navItem.getCountProblems());

        return v;
    }
}

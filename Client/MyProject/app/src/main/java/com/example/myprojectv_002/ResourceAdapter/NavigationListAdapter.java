package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.Navigation_Item;

import java.util.List;

public class NavigationListAdapter extends ArrayAdapter<Navigation_Item> {
    Context context;
    int resLayout;
    List<Navigation_Item> listNavItems;
    View v;

    public NavigationListAdapter(Context context, int resLayout, List<Navigation_Item> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context=context;
        this.resLayout=resLayout;
        this.listNavItems=listNavItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.title);
        ImageView navIcon = (ImageView) v.findViewById(R.id.imageIcon);

        Navigation_Item navItem = listNavItems.get(position);

        tvTitle.setText(navItem.getTitle());
        navIcon.setImageResource(navItem.getResIcon());

        return v;
    }
}

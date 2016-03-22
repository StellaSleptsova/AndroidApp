package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.Setting_item;

import java.util.List;

public class Setting_adapter extends ArrayAdapter<Setting_item> {
    Context context;
    int resLayout;
    List<Setting_item> listItems;

    public Setting_adapter(Context context, int resLayout, List<Setting_item> listMessItems) {
        super(context, resLayout, listMessItems);

        this.context=context;
        this.resLayout=resLayout;
        this.listItems=listMessItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.text_title_setting);
        ImageView navIcon = (ImageView) v.findViewById(R.id.image_setting);
        TextView lsMess=(TextView)v.findViewById(R.id.text_subtitle_setting);

        Setting_item navItem = listItems.get(position);

        tvTitle.setText(navItem.getTitleSetting());
        navIcon.setImageResource(navItem.getResIcon());
        lsMess.setText(navItem.getSubtitleSetting());

        return v;
    }
}

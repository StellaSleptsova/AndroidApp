package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.Problem_item;

import java.util.List;

public class Problem_adapter extends ArrayAdapter<Problem_item> {
    Context context;
    int resLayout;
    List<Problem_item> listItems;

    public Problem_adapter(Context context, int resLayout, List<Problem_item> listItems) {
        super(context, resLayout, listItems);
        this.context=context;
        this.resLayout=resLayout;
        this.listItems=listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.text_name_problem);
        TextView cP=(TextView)v.findViewById(R.id.text_start_problem);

        Problem_item navItem = listItems.get(position);

        tvTitle.setText(navItem.getNameProblem());
        cP.setText(navItem.getStartTextProblem());

        return v;
    }
}

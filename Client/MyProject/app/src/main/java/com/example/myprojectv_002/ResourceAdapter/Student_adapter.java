package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.Student_item;

import java.util.List;

public class Student_adapter extends ArrayAdapter<Student_item> {

    Context context;
    int resLayout;
    List<Student_item> listItems;

    public Student_adapter(Context context, int resLayout, List<Student_item> listItems) {
        super(context, resLayout, listItems);

        this.context=context;
        this.resLayout=resLayout;
        this.listItems=listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context,resLayout,null);

        TextView tvTitle = (TextView) v.findViewById(R.id.text_nameStudent);
        TextView countSolveProblem=(TextView)v.findViewById(R.id.text_countSolveProblems);
        //TextView countProblem=(TextView)v.findViewById(R.id.text_countProblems);

        Student_item navItem = listItems.get(position);

        tvTitle.setText(navItem.getName());
        countSolveProblem.setText(navItem.getCountSolveProblems().toString());
        //countProblem.setText(navItem.getCountProblems());

        return v;
    }
}

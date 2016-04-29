package com.example.myprojectv_002.ResourceAdapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.R;

import java.util.List;

public class Adapter_forListView_Student_ofGroup extends ArrayAdapter<StudentInfo> {
    Context context;
    int resLayout;
    List<StudentInfo> listStudents;

    public Adapter_forListView_Student_ofGroup(Context context, int resLayout, List<StudentInfo> listItems) {
        super(context,resLayout,listItems);
        this.context=context;
        this.resLayout=resLayout;
        this.listStudents=listItems;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View v=View.inflate(context,resLayout,null);
        TextView tv_nameObject=(TextView)v.findViewById(R.id.tv_name_object_forGroup);
        TextView tv_solved=(TextView)v.findViewById(R.id.tv_countSolvedTasks);
        TextView tv_unsolved=(TextView)v.findViewById(R.id.tv_countUnsolvedTasks);

        StudentInfo item=listStudents.get(position);

        tv_nameObject.setText(item.nameStudent);
        tv_solved.setText(Integer.toString(item.count_solvedTask));
        tv_unsolved.setText(Integer.toString(item.count_unsolvedTask));
        return v;
    }
}

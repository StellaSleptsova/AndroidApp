package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myprojectv_002.ClassesObject.TaskInfo;
import com.example.myprojectv_002.R;

import java.util.List;

public class Adapter_forListView_Task_ofGroup extends ArrayAdapter<TaskInfo> {
    Context context;
    int resLayout;
    List<TaskInfo> listTask;

    public Adapter_forListView_Task_ofGroup(Context context, int resLayout, List<TaskInfo> listItems) {
        super(context,resLayout,listItems);
        this.context=context;
        this.resLayout=resLayout;
        this.listTask=listItems;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View v=View.inflate(context,resLayout,null);
        TextView tv_nameObject=(TextView)v.findViewById(R.id.tv_name_object_forGroup);
        TextView tv_solved=(TextView)v.findViewById(R.id.tv_countSolvedTasks);
        TextView tv_unsolved=(TextView)v.findViewById(R.id.tv_countUnsolvedTasks);

        TaskInfo item=listTask.get(position);

        tv_nameObject.setText(item.nameTask);
        tv_solved.setText("");
        tv_unsolved.setText("");
        return v;
    }
}

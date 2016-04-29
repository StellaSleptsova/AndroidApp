package com.example.myprojectv_002.ResourceItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myprojectv_002.R;

public class ViewHolder_Tasks extends RecyclerView.ViewHolder {
    public TextView tv_nameTask;
    public TextView tv_dateTask;
    public TextView tv_textTask;
    public Button button_show_textOfTtask;
    public Button button_show_menu;
    public Context context;
    public boolean buttonShowClicked = false;
    public boolean textIsget=false;

    public ViewHolder_Tasks(View v) {
        super(v);
        tv_nameTask = (TextView) v.findViewById(R.id.text_tasks_name);
        tv_textTask = (TextView) v.findViewById(R.id.tv_text_task);
        tv_dateTask = (TextView) v.findViewById(R.id.task_info);
        button_show_textOfTtask = (Button) v.findViewById(R.id.button_show_textTask);
        button_show_menu = (Button) v.findViewById(R.id.button_show_menu_task);
        context = v.getContext();
    }
}

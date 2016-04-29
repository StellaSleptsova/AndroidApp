package com.example.myprojectv_002.ResourceItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myprojectv_002.R;

public class ViewHolder_Students  extends RecyclerView.ViewHolder {
    public TextView tv_namestudent;
    public TextView tv_institution;
    public Button button_show_list_group;
    public Button button_send_message;
    public Button button_show_menu;

    public ListView listGroup;
    public Context context;
    //public List<ListOfGroupForStudent_item> listItem;
    //public listAdapter listAdapter;
    public boolean buttonShowClicked = false;

    public ViewHolder_Students(View v) {
        super(v);
        tv_namestudent = (TextView) v.findViewById(R.id.text_student_name);
        tv_institution = (TextView) v.findViewById(R.id.student_info);
        button_show_list_group = (Button) v.findViewById(R.id.button_show_listGroup);
        button_send_message = (Button) v.findViewById(R.id.button_send_message);
        button_show_menu = (Button) v.findViewById(R.id.button_show_menu_student);
        //listView=(ListView)v.findViewById(R.id.list_students__nav);
        context = v.getContext();
    }
}
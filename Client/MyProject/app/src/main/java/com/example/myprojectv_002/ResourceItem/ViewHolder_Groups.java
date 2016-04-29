package com.example.myprojectv_002.ResourceItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.ClassesObject.TaskInfo;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.Adapter_forListView_Student_ofGroup;
import com.example.myprojectv_002.ResourceAdapter.Adapter_forListView_Task_ofGroup;

import java.util.List;

public class ViewHolder_Groups extends RecyclerView.ViewHolder {
    public TextView tv_nameGroup;
    public TextView tv_institution;
    public Button button_show_table;
    public Button button_show_menu;
    public TextView tv_count_student;
    public TextView tv_count_task;
    public ImageView img_list_students;
    public ImageView img_list_tasks;
    public ListView listView_student_or_task;
    public Adapter_forListView_Student_ofGroup adapter_forListView_student_ofGroup;
    public Adapter_forListView_Task_ofGroup adapter_forListView_task_ofGroup;
    public ViewGroup.LayoutParams params ;
    public List<StudentInfo> listStudents=null;
    public List<TaskInfo> listTasks=null;
    public Context context;
    public RelativeLayout relativeLayout_listStudents;
    public RelativeLayout relativeLayout_listTasks;
    public boolean buttonShow_listStudent_Is_Clicked = false;
    public boolean buttonShow_listTasks_Is_Clicked=false;
    public boolean listStudents_isGet=false;
    public boolean listTasks_isGet=false;

    public ViewHolder_Groups(View v) {
        super(v);
        tv_nameGroup = (TextView) v.findViewById(R.id.text_group_name);
        tv_institution = (TextView) v.findViewById(R.id.group_info);
        tv_count_student = (TextView) v.findViewById(R.id.tv_count_student);
        tv_count_task = (TextView) v.findViewById(R.id.tv_count_task);
        button_show_table = (Button) v.findViewById(R.id.button_show_table);
        listView_student_or_task = (ListView) v.findViewById(R.id.list_students_and_task);
        button_show_menu = (Button) v.findViewById(R.id.button_show_menu_group);
        relativeLayout_listStudents=(RelativeLayout)v.findViewById(R.id.rLayout_button_list_student);
        relativeLayout_listTasks=(RelativeLayout)v.findViewById(R.id.rLayout_button_list_task);
        img_list_students=(ImageView)v.findViewById(R.id.img_list_student);
        img_list_tasks=(ImageView)v.findViewById(R.id.img_list_task);
        params=listView_student_or_task.getLayoutParams();
        //listView=(ListView)v.findViewById(R.id.list_students__nav);
        context = v.getContext();
    }
}
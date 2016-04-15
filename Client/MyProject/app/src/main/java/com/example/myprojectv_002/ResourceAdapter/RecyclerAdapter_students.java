package com.example.myprojectv_002.ResourceAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.ListOfGroupForStudent_item;

import java.util.List;

public class RecyclerAdapter_students extends RecyclerView.Adapter<RecyclerAdapter_students.ViewHolder>{
    private List<StudentInfo> listStudents;
    public RecyclerAdapter_students(List<StudentInfo> listSt){
        listStudents=listSt;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_namestudent;
        public TextView tv_institution;
        public Button button_show_list_group;
        public Button button_send_message;

        public ListView listGroup;
        public Context context;
        //public List<ListOfGroupForStudent_item> listItem;
        public listAdapter listAdapter;
        public boolean buttonShowClicked = false;

        public ViewHolder(View v){
            super(v);
            tv_namestudent=(TextView)v.findViewById(R.id.text_student_name);
            tv_institution=(TextView)v.findViewById(R.id.student_info);
            button_show_list_group=(Button)v.findViewById(R.id.button_show_listGroup);
            button_send_message=(Button)v.findViewById(R.id.button_send_message);
            //listView=(ListView)v.findViewById(R.id.list_students__nav);
            context=v.getContext();
        }
    }

    public static class listAdapter extends ArrayAdapter<ListOfGroupForStudent_item>{
        Context context;
        int resLayout;
        List<ListOfGroupForStudent_item> listItems;

        public listAdapter(Context context, int resLayout,List<ListOfGroupForStudent_item> listitems) {
            super(context, resLayout, listitems);
            this.context = context;
            this.resLayout = resLayout;
            this.listItems = listitems;
        }

        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View v=View.inflate(context,resLayout,null);
            TextView tv_nameGroup = (TextView)v.findViewById(R.id.text_name_group_for_student);
            TextView tv_solveTasks=(TextView)v.findViewById(R.id.text_count_markTasks);
            TextView tv_unsolveTasks=(TextView)v.findViewById(R.id.text_count_unmarkTask);

            ListOfGroupForStudent_item item=listItems.get(position);

            tv_nameGroup.setText(item.getNameGroup());
            tv_solveTasks.setText(item.getSolveTasks()+"");
            tv_unsolveTasks.setText(item.getUnSolveTasks()+"");

            return v;
        }
    }

    @Override
    public RecyclerAdapter_students.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_students, parent, false);
        ViewHolder vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.tv_namestudent.setText(listStudents.get(position).nameStudent);
        holder.tv_institution.setText(listStudents.get(position).institution);
        /*holder.button_show_list_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.buttonShowClicked) {
                    holder.listView.setVisibility(v.GONE);
                    holder.buttonShowClicked=false;
                    holder.button_show_list_group.setBackgroundResource(R.mipmap.ic_keyboard_arrow_up);
                } else {
                    if (holder.listItem != null) {
                        holder.listView.setVisibility(v.VISIBLE);
                    } else {
                        holder.listItem = new ArrayList<ListOfGroupForStudent_item>();
                        holder.listItem.add(new ListOfGroupForStudent_item("341 group", 13, 15));
                        holder.listItem.add(new ListOfGroupForStudent_item("231 group", 10, 15));
                        holder.listItem.add(new ListOfGroupForStudent_item("141 group", 3, 17));
                        holder.listAdapter = new listAdapter(holder.context, R.layout.item_list_group_for_student, holder.listItem);
                        holder.listView.setAdapter(holder.listAdapter);
                     }
                    holder.button_show_list_group.setBackgroundResource(R.mipmap.ic_keyboard_arrow_down);
                    ViewGroup.LayoutParams params=holder.listView.getLayoutParams();
                    params.height=53*holder.listItem.size();
                    holder.listView.setLayoutParams(params);
                    holder.buttonShowClicked = true;
                }
            }
        });*/
    }

    @Override
    public int getItemCount(){
        return listStudents.size();
    }
}

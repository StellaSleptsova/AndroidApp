package com.example.myprojectv_002.ResourceAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.Fragments.PopUpMenuEventHandle;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.ViewHolder_Students;

import java.util.List;

public class RecyclerAdapter_students extends RecyclerView.Adapter<ViewHolder_Students> {
    private List<StudentInfo> listStudents;

    public RecyclerAdapter_students(List<StudentInfo> listSt) {
        listStudents = listSt;
    }



    /*public static class listAdapter extends ArrayAdapter<ListOfGroupForStudent_item>{
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
    }*/

    @Override
    public ViewHolder_Students onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_students, parent, false);
        ViewHolder_Students vh = new ViewHolder_Students(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder_Students holder, final int position) {
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
                        holder.listAdapter = new listAdapter(holder.context, R.layout.tem_list_group_for_student, holder.listItem);
                        holder.listView.setAdapter(holder.listAdapter);
                     }
                    holder.button_show_list_group.setBackgroundResource(R.mipmap.ic_chevron_down);
                    ViewGroup.LayoutParams params=holder.listView.getLayoutParams();
                    params.height=53*holder.listItem.size();
                    holder.listView.setLayoutParams(params);
                    holder.buttonShowClicked = true;
                }
            }
        });*/
        holder.button_show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                PopUpMenuEventHandle popUpMenuEventHandle = new PopUpMenuEventHandle(holder.context, "Student", listStudents.get(position).idStudent);
                popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
                menuInflater.inflate(R.menu.menu_for_card, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }
}

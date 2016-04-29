package com.example.myprojectv_002.ResourceAdapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.GroupInfo;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.ClassesObject.TaskInfo;
import com.example.myprojectv_002.CreateRequest.GetList_forGroup;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.Fragments.PopUpMenuEventHandle;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.ViewHolder_Groups;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter_groups extends RecyclerView.Adapter<ViewHolder_Groups> {

    private List<GroupInfo> listGroup;

    public RecyclerAdapter_groups(List<GroupInfo> listGr) {
        listGroup = listGr;
    }

    @Override
    public ViewHolder_Groups onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_groups, parent, false);
        ViewHolder_Groups vh = new ViewHolder_Groups(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder_Groups holder, final int position) {
        holder.tv_nameGroup.setText(listGroup.get(position).nameGroup);
        holder.tv_institution.setText(listGroup.get(position).institution);
        holder.tv_count_student.setText(Integer.toString(listGroup.get(position).countStudents));
        holder.tv_count_task.setText(Integer.toString(listGroup.get(position).countProblems));

        holder.relativeLayout_listStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // COUNT = 0?!?!?!?!?!?!??!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?
                // !?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!??!?!
                holder.relativeLayout_listStudents.setClickable(false);
                if (!holder.listStudents_isGet) {
                    holder.listStudents_isGet = true;
                    holder.buttonShow_listStudent_Is_Clicked=true;
                    holder.buttonShow_listTasks_Is_Clicked=false;
                    holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_down);
                    holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_up);
                    holder.relativeLayout_listTasks.setBackgroundResource(R.color.colorWhite);
                    holder.relativeLayout_listStudents.setBackgroundResource(R.color.colorList);
                    GetList_Async async = new GetList_Async();
                    async.setHolder(holder);
                    async.setStrObj("Students");
                    async.execute(Integer.toString(listGroup.get(position).idGroup));
                } else {
                    if (holder.buttonShow_listStudent_Is_Clicked) {
                        holder.relativeLayout_listStudents.setBackgroundResource(R.color.colorWhite);
                        holder.listView_student_or_task.setVisibility(v.GONE);
                        holder.buttonShow_listStudent_Is_Clicked = false;
                        holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_up);
                        holder.relativeLayout_listStudents.setClickable(true);
                    } else {
                        holder.relativeLayout_listStudents.setBackgroundResource(R.color.colorList);
                        holder.relativeLayout_listTasks.setBackgroundResource(R.color.colorWhite);
                       /* if (holder.buttonShow_listTasks_Is_Clicked) {*/
                        //holder.listView_student_or_task.setMinimumHeight(50 * holder.listStudents.size());
                        holder.params.height = 43 * holder.listStudents.size();
                        holder.listView_student_or_task.setLayoutParams(holder.params);
                        holder.listView_student_or_task.setAdapter(holder.adapter_forListView_student_ofGroup);
                        holder.buttonShow_listTasks_Is_Clicked = false;
                        /*} else {
                            /*holder.listView_student_or_task.setVisibility(v.GONE);
                            holder.buttonShow_listStudent_Is_Clicked = false;
                            holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_up);
                            holder.relativeLayout_listStudents.setClickable(true);*/
                        /*}*/
                        holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_up);
                        holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_down);
                        holder.buttonShow_listStudent_Is_Clicked = true;
                        holder.listView_student_or_task.setVisibility(v.VISIBLE);
                        holder.relativeLayout_listStudents.setClickable(true);
                    }
                }
            }
        });

        holder.relativeLayout_listTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relativeLayout_listTasks.setClickable(false);
                if (!holder.listTasks_isGet) {
                    holder.listTasks_isGet = true;
                    holder.buttonShow_listTasks_Is_Clicked=true;
                    holder.buttonShow_listStudent_Is_Clicked=false;
                    holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_down);
                    holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_up);
                    holder.relativeLayout_listTasks.setBackgroundResource(R.color.colorList);
                    holder.relativeLayout_listStudents.setBackgroundResource(R.color.colorWhite);
                    GetList_Async async = new GetList_Async();
                    async.setHolder(holder);
                    async.setStrObj("Tasks");
                    async.execute(Integer.toString(listGroup.get(position).idGroup));
                } else {
                    if (holder.buttonShow_listTasks_Is_Clicked) {
                        holder.relativeLayout_listTasks.setBackgroundResource(R.color.colorWhite);
                        holder.listView_student_or_task.setVisibility(v.GONE);
                        holder.buttonShow_listTasks_Is_Clicked = false;
                        holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_up);
                        holder.relativeLayout_listTasks.setClickable(true);
                    } else {
                       /* if (holder.buttonShow_listTasks_Is_Clicked) {*/
                        //holder.listView_student_or_task.setMinimumHeight(50 * holder.listTasks.size());
                        holder.params.height = 43 * holder.listTasks.size();
                        holder.listView_student_or_task.setLayoutParams(holder.params);
                        holder.listView_student_or_task.setAdapter(holder.adapter_forListView_task_ofGroup);
                        holder.buttonShow_listStudent_Is_Clicked = false;
                        holder.relativeLayout_listStudents.setBackgroundResource(R.color.colorWhite);
                        holder.relativeLayout_listTasks.setBackgroundResource(R.color.colorList);
                        /*} else {
                            /*holder.listView_student_or_task.setVisibility(v.GONE);
                            holder.buttonShow_listStudent_Is_Clicked = false;
                            holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_up);
                            holder.relativeLayout_listStudents.setClickable(true);*/
                        /*}*/
                        holder.img_list_students.setBackgroundResource(R.mipmap.ic_chevron_up);
                        holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_down);
                        holder.buttonShow_listTasks_Is_Clicked = true;
                        holder.listView_student_or_task.setVisibility(v.VISIBLE);
                        holder.relativeLayout_listTasks.setClickable(true);
                    }
                }
               /* holder.relativeLayout_listTasks.setClickable(false);
                if (holder.buttonShow_listTasks_Is_Clicked) {
                    holder.listView_student_or_task.setVisibility(v.GONE);
                    holder.buttonShow_listTasks_Is_Clicked = false;
                    holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_up);
                    holder.relativeLayout_listTasks.setClickable(true);
                } else {
                    holder.img_list_tasks.setBackgroundResource(R.mipmap.ic_chevron_down);
                    holder.buttonShow_listTasks_Is_Clicked = true;
                    if (!holder.listTasks_isGet) {
                        holder.listTasks_isGet = true;
                        GetList_Async async = new GetList_Async();
                        async.setHolder(holder);
                        async.setStrObj("Tasks");
                        async.execute(Integer.toString(listGroup.get(position).idGroup));
                    } else {
                        holder.listView_student_or_task.setVisibility(View.VISIBLE);
                        holder.relativeLayout_listTasks.setClickable(true);
                    }
                }*/
            }
        });

        holder.button_show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                PopUpMenuEventHandle popUpMenuEventHandle = new PopUpMenuEventHandle(holder.context, "Group", listGroup.get(position).idGroup);
                popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
                menuInflater.inflate(R.menu.menu_for_card, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public class GetList_Async extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;
        private GetList_forGroup getList_forGroup = null;
        private ViewHolder_Groups holder;
        private String strObj;

        public void setHolder(ViewHolder_Groups hol) {
            holder = hol;
        }
        public void setStrObj(String str){strObj=str;}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            sendRequest = new SendRequest();
            getList_forGroup = new GetList_forGroup();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                Activity_Navigation.progressDialog.dismiss();
                switch (getList_forGroup.getResponse(ret)) {
                    case "ok":
                        if (strObj.equals("Students")) {
                            holder.adapter_forListView_student_ofGroup = new Adapter_forListView_Student_ofGroup(holder.context, R.layout.item_for_listview_ingroup, holder.listStudents);
                            holder.listView_student_or_task.setAdapter(holder.adapter_forListView_student_ofGroup);
                            //holder.listView_student_or_task.setMinimumHeight(50 * holder.listStudents.size());
                            holder.params.height = 43 * holder.listStudents.size();
                            /*holder.listitems = new ArrayList<item_forList_inGroups>();
                            for (int i = 0; i < holder.listStudents.size(); i++) {
                                item_forList_inGroups item = new item_forList_inGroups();
                                item.setStudentInfo(holder.listStudents.get(i));
                                item.set_item_forList_inGroups();
                                holder.listitems.add(item);
                            }*/
                            holder.relativeLayout_listStudents.setClickable(true);
                        } else {
                            holder.adapter_forListView_task_ofGroup = new Adapter_forListView_Task_ofGroup(holder.context, R.layout.item_for_listview_ingroup, holder.listTasks);
                            holder.listView_student_or_task.setAdapter(holder.adapter_forListView_task_ofGroup);
                           // holder.listView_student_or_task.setMinimumHeight(50*holder.listTasks.size());
                            holder.params.height = 43 * holder.listTasks.size();
                            /*holder.listitems = new ArrayList<item_forList_inGroups>();
                            for (int i = 0; i < holder.listTasks.size(); i++) {
                                item_forList_inGroups item = new item_forList_inGroups();
                                item.setTaskInfo(holder.listTasks.get(i));
                                item.set_item_forList_inGroups();
                                holder.listitems.add(item);
                            }*/
                            holder.relativeLayout_listTasks.setClickable(true);
                        }
                        //holder.adapter_forListView = new Adapter_forListView_ofGroup(holder.context, R.layout.item_for_listview_ingroup, holder.listitems);
                        //holder.listView_student_or_task.setAdapter(holder.adapter_forListView);
                        //ViewGroup.LayoutParams params = holder.listView_student_or_task.getLayoutParams();
                        //params.height = 40 * holder.listitems.size();
                        holder.listView_student_or_task.setLayoutParams(holder.params);
                        holder.listView_student_or_task.setVisibility(View.VISIBLE);
                        break;
                    case "error":
                        System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        /*TaskInfo taskInfo = new TaskInfo();
                        taskInfo.nameTask = "ERROR from server";*/
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... data) {
            try {
                InetAddress serverAddr = InetAddress.getByName(ServerInfo.getIP());
                socketClient = new Socket(serverAddr, ServerInfo.getPort());
                if (strObj.equals("Tasks")) {
                    holder.listTasks = new ArrayList<TaskInfo>();
                    getList_forGroup.setListTasks(holder.listTasks);
                } else {
                    holder.listStudents = new ArrayList<StudentInfo>();
                    getList_forGroup.setListStudents(holder.listStudents);
                }
                return (sendRequest.SendAndGet(socketClient, getList_forGroup.createRequest(data[0],strObj)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}


package com.example.myprojectv_002.ResourceAdapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.TaskInfo;
import com.example.myprojectv_002.CreateRequest.GetTextTask;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.Fragments.PopUpMenuEventHandle;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceItem.ViewHolder_Tasks;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class RecyclerAdapter_tasks extends RecyclerView.Adapter<ViewHolder_Tasks> {
    private List<TaskInfo> listTasks;

    public RecyclerAdapter_tasks(List<TaskInfo> listTs) {
        listTasks = listTs;
    }

    @Override
    public ViewHolder_Tasks onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_tasks, parent, false);
        ViewHolder_Tasks vh = new ViewHolder_Tasks(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder_Tasks holder, final int position) {
        holder.tv_nameTask.setText(listTasks.get(position).nameTask);
        holder.tv_dateTask.setText(listTasks.get(position).dateTask);
        holder.button_show_textOfTtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.buttonShowClicked) {
                    holder.buttonShowClicked = false;
                    holder.button_show_textOfTtask.setBackgroundResource(R.mipmap.ic_chevron_up);
                    holder.tv_textTask.setVisibility(View.GONE);
                } else {
                    holder.tv_textTask.setVisibility(View.VISIBLE);
                    holder.button_show_textOfTtask.setBackgroundResource(R.mipmap.ic_chevron_down);
                    holder.buttonShowClicked = true;
                    if (!holder.textIsget) {
                        holder.textIsget=true;
                        GetTextTask_Async textTask_async = new GetTextTask_Async();
                        textTask_async.setHolder(holder);
                        textTask_async.setPosition(position);
                        textTask_async.execute(listTasks.get(position).idTask);
                    }
                }
            }
        });
        holder.button_show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                PopUpMenuEventHandle popUpMenuEventHandle = new PopUpMenuEventHandle(holder.context, "Task", listTasks.get(position).idTask);
                popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
                menuInflater.inflate(R.menu.menu_for_card, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public class GetTextTask_Async extends AsyncTask<Integer,Void,String> {
        private Socket socketClient=null;
        private SendRequest sendRequest=null;
        private GetTextTask getTextTask=null;
        private ViewHolder_Tasks holder;
        private int position;

        public void setHolder(ViewHolder_Tasks hol){
            holder=hol;
        }
        public void setPosition(int i){
            position=i;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            sendRequest = new SendRequest();
            getTextTask=new GetTextTask();
        }

        @Override
        protected void onPostExecute(final String ret){
            try{
                socketClient.close();
                System.out.println(ret);
                Activity_Navigation.progressDialog.dismiss();
                switch (getTextTask.getResponse(ret,listTasks.get(position))) {
                    case "ok":
                        holder.tv_textTask.setText(listTasks.get(position).textTask+"\n");
                        break;
                    case "error":
                        holder.tv_textTask.setText("ERROR from server"+"\n");
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Integer... data) {
            try{
                InetAddress serverAddr=InetAddress.getByName(ServerInfo.getIP());
                socketClient=new Socket(serverAddr,ServerInfo.getPort());
                System.out.println("1");
                return (sendRequest.SendAndGet(socketClient, getTextTask.createRequest(data[0])));
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

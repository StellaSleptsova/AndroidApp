package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.TaskInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.ListTasks;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter__empty;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter_tasks;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class fragments_navigation_item_tasks extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter_tasks mAdapter;
    public static ListTasks listTasks = null;
    public boolean isChange = false;
    static View v;
    public static FloatingActionButton fab_task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity_Navigation.toolbar.setTitle("Задачи");
        if (listTasks == null || isChange) {
            v = inflater.inflate(R.layout.fragment_tasks, container, false);

            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_tasks);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            isChange = false;
            GetListOfTasks getTasks = new GetListOfTasks();
            getTasks.execute();
        }
        return v;
    }

    private void initAdapterTask(List<TaskInfo> listTask) {
        mAdapter = new RecyclerAdapter_tasks(listTask);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEmptyCard() {
        RecyclerAdapter__empty recyclerAdapter__empty = new RecyclerAdapter__empty
                ("У вас нет задач.\nДобавьте задачу,\n нажав на  +");
        mRecyclerView.setAdapter(recyclerAdapter__empty);
        listTasks = null;
    }

    public void initFab() {
        fab_task = (FloatingActionButton) v.findViewById(R.id.fab_list_tasks);
        fab_task.setVisibility(View.VISIBLE);
        fab_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_add_task fragment_add_task = new fragment_add_task();
                Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.clayout_list_tasks, fragment_add_task).addToBackStack("stack").commit();
                fab_task.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class GetListOfTasks extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            listTasks = new ListTasks();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                Activity_Navigation.progressDialog.dismiss();
                switch (listTasks.getResponse(ret)) {
                    case "yesTasks":
                        initAdapterTask(listTasks.getTasks());
                        break;
                    case "noTasks":
                        isChange = true;
                        initEmptyCard();
                        break;
                }
                initFab();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... data) {
            try {
                InetAddress serverAddr = InetAddress.getByName(ServerInfo.getIP());
                socketClient = new Socket(serverAddr, ServerInfo.getPort());
                return (sendRequest.SendAndGet(socketClient, listTasks.createRequest(UserInfo.idUser)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }

}
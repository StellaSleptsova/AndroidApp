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
import com.example.myprojectv_002.ClassesObject.GroupInfo;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.ListGroups;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter__empty;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter_groups;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class fragments_navigation_item_groups extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter_groups mAdapter;
    public  ListGroups listGroups = null;
    public boolean isChange = false;
    static View v;
    public static   FloatingActionButton fab_group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity_Navigation.toolbar.setTitle("Группы");

        if (listGroups == null || isChange) {
            v = inflater.inflate(R.layout.fragment_groups, container, false);

            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_group);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            isChange = false;
            GetListOfGroup getStudent = new GetListOfGroup();
            getStudent.execute();
        }
        return v;
    }

    private void initListGroup(List<GroupInfo> listGroup) {
        mAdapter = new RecyclerAdapter_groups(listGroup);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEmptyCard() {
        RecyclerAdapter__empty recyclerAdapter__empty = new RecyclerAdapter__empty
                ("У вас нет групп.\nДобавьте группу,\n нажав на  +");
        mRecyclerView.setAdapter(recyclerAdapter__empty);
    }

    public void initFab() {
        fab_group = (FloatingActionButton) v.findViewById(R.id.fab_list_group);
        fab_group.setVisibility(View.VISIBLE);
        fab_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_add_group fragment_add_group = new fragment_add_group();
                Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.clayout_list_group, fragment_add_group).addToBackStack("stack").commit();
                fab_group.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class GetListOfGroup extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            listGroups = new ListGroups();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                Activity_Navigation.progressDialog.dismiss();
                switch (listGroups.getResponse(ret)) {
                    case "YesGroups":
                        initListGroup(listGroups.getGroups());
                        break;
                    case "noGroup":
                        initEmptyCard();
                        isChange = true;
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
                return (sendRequest.SendAndGet(socketClient, listGroups.createRequest(UserInfo.idUser)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}
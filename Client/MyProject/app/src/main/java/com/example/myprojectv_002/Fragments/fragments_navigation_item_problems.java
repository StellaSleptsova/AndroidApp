package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.GroupInfo;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.ListGroups;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.GroupProblems_adapter;
import com.example.myprojectv_002.ResourceItem.GroupProblems_item;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class fragments_navigation_item_problems extends Fragment {

    int countGroups;
    GroupProblems_adapter ListAdapter;
    ListView listView;
    List<GroupInfo> groups;
    List<GroupProblems_item> listItem;
    Boolean yesGroup=true;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_problems, container, false);

        Activity_Navigation.toolbar.setTitle("Группы: задачи");
        ListGroupTask at = new ListGroupTask();
        at.execute();
        return v;
    }

    private void initListGroup(List<GroupInfo> listGroups) {

        listItem = new ArrayList<GroupProblems_item>();
        groups = listGroups;
        countGroups = groups.size();

        if(countGroups==0){
            yesGroup=false;
            listItem.add(new GroupProblems_item("У вас пока нет групп. \n Добавьте группу в разделе <Группы>",""));
        }
        else {
            yesGroup=true;
            for (int i = 0; i < countGroups; i++) {
                listItem.add(new GroupProblems_item(groups.get(i).nameGroup, groups.get(i).countProblems.toString()));
            }
        }

        listView = (ListView) v.findViewById(R.id.list_groups_problems);
        ListAdapter = new GroupProblems_adapter(getActivity(), R.layout.item_list_group_problems, listItem);
        listView.setAdapter(ListAdapter);

        if(yesGroup) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fragment_list_problems fragment_list_problems=new fragment_list_problems();
                    fragment_list_problems.setIdGroup(groups.get(position).idGroup);
                    Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_list_problems).addToBackStack("stack").commit();
                }
            });
        }
    }

    public class ListGroupTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private ListGroups listGroups = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listGroups = new ListGroups();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (listGroups.getResponse(ret)) {
                    case "YesGroups":
                        initListGroup(listGroups.getGroups());
                        break;
                    case "error":
                        /*ERROR*/
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
                System.out.println(serverAddr);
                socketClient = new Socket(serverAddr, ServerInfo.getPort());
                return (sendRequest.SendAndGet(socketClient, listGroups.createRequest(UserInfo.idUser)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

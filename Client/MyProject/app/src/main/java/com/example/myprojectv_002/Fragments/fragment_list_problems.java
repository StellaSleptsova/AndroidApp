package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ProblemInfo;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.ListProblems;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.Problem_adapter;
import com.example.myprojectv_002.ResourceItem.Problem_item;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class fragment_list_problems extends Fragment {

    static FloatingActionButton fab_problem;
    Boolean yesProblems;
    ListView listView;
    List<Problem_item> listItem;
    Integer idGroup;
    List<ProblemInfo> listProblems;
    Problem_adapter ListAdapter;
    View v;

    public void setIdGroup(Integer i){
        this.idGroup=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_list_problems, container, false);

        Activity_Navigation.toolbar.setTitle("Список задач");
        ListProblemTask at = new ListProblemTask();
        at.execute();

        return v;
    }

    private void initListProblem(List<ProblemInfo> list){

        listItem = new ArrayList<Problem_item>();
        listProblems=list;
        int l=listProblems.size();

        if(l==0) {
            yesProblems=false;
            listItem.add(new Problem_item("В данной группе нет задач. \n Добавьте задачу нажав на <+>",""));
        }
        else {
            yesProblems=true;
            for (int i = 0; i < l; i++) {
                listItem.add(new Problem_item(listProblems.get(i).nameProblem, listProblems.get(i).starTextProblem));
            }
        }

        listView = (ListView) v.findViewById(R.id.list_problems);
        ListAdapter = new Problem_adapter(getActivity(), R.layout.item_list_problems, listItem);
        listView.setAdapter(ListAdapter);

        if(yesProblems) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fragment_text_problem fragment_text_problem=new fragment_text_problem();
                    fragment_text_problem.setIdProblem(listProblems.get(position).idProblem);
                     Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_text_problem).addToBackStack("stack").commit();
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    fragment_menu fragment_menu = new fragment_menu();
                    fragment_menu.setNameAction("Problem");
                    fragment_menu.setId(listProblems.get(position).idProblem);
                    fragment_menu.setIdGroup(idGroup);
                    Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.layout_list_problem, fragment_menu).addToBackStack("stack").commit();
                    fab_problem.setVisibility(View.INVISIBLE);
                    return true;
                }
            });
        }

        fab_problem =(FloatingActionButton)v.findViewById(R.id.fab_problem);
        fab_problem.setVisibility(View.VISIBLE);
        fab_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_add_problem listP = new fragment_add_problem();
                listP.setIdGroup(idGroup);
                Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.layout_list_problem, listP).addToBackStack("stack").commit();
                fab_problem.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class ListProblemTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private ListProblems listProblems = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            listProblems = new ListProblems();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (listProblems.getResponse(ret)) {
                    case "yesProblems":
                        initListProblem(listProblems.getProblems());
                        break;
                    case "noProblems":
                        initListProblem(null);
                        break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... data) {
            try {
                InetAddress serverAddr = InetAddress.getByName(ServerInfo.getIP());
                System.out.println(serverAddr);
                socketClient = new Socket(serverAddr, ServerInfo.getPort());
                return (sendRequest.SendAndGet(socketClient, listProblems.createRequest(idGroup)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

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
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.CreateRequest.ListStudents;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.Student_adapter;
import com.example.myprojectv_002.ResourceItem.Student_item;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class fragment_list_student extends Fragment {

    FloatingActionButton fab_student;
    Student_adapter ListAdapter;
    Boolean yesStudent;
    List<StudentInfo> listStudents;
    ListView listView;
    List<Student_item> listItem;
    Integer idGroup;
    View v;

    public void setIdGroup(Integer id){
        this.idGroup=id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_students, container, false);
        Activity_Navigation.toolbar.setTitle("Список студентов");

        ListStudentsTask at = new ListStudentsTask();
        at.execute();
        return v;
    }

    private void initListStudent(List<StudentInfo> listS){

        listView=(ListView)v.findViewById(R.id.list_students);
        listItem=new ArrayList<Student_item>();
        listStudents = listS;
        int countStudents= listStudents.size();

        if(countStudents==0){
            yesStudent=false;
            listItem.add(new Student_item("В данной группе нет студентов. \n Добавьте студентов нажав на +",0,0));
        }
        else {
            yesStudent=true;
            for (int i = 0; i < countStudents; i++) {
                //listItem.add(new Student_item(listStudents.get(i).nameStudent, listStudents.get(i).countSolveProblems, listStudents.get(i).countProblem));
            }
        }

        ListAdapter = new Student_adapter(getActivity(), R.layout.item_list_students, listItem);
        listView.setAdapter(ListAdapter);

        if(yesStudent) {

            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, listFragment.get(position)).addToBackStack("stack").commit();
                }
            });*/

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    fragment_menu fragment_menu = new fragment_menu();
                    fragment_menu.setNameAction("Student");
                    fragment_menu.setId(listStudents.get(position).idStudent);
                    fragment_menu.setIdGroup(idGroup);
                    Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.layout_list_student, fragment_menu).addToBackStack("stack").commit();
                    fab_student.setVisibility(View.INVISIBLE);
                    return true;
                }
            });
        }

        fab_student = (FloatingActionButton) v.findViewById(R.id.fab_student);
        fab_student.setVisibility(View.VISIBLE);
        fab_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_add_student fragment_add_student=new fragment_add_student();
                fragment_add_student.setIdGroup(idGroup);
                Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.layout_list_student, fragment_add_student).addToBackStack("stack").commit();
                fab_student.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class ListStudentsTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private ListStudents listStudents = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listStudents = new ListStudents();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (listStudents.getResponse(ret)) {
                    case "yesStudents":
                        initListStudent(listStudents.getStudents());
                        break;
                    case "noStudents":
                        initListStudent(null);
                        /*Добавить студента*/
                        /*System.out.println("noStudents");
                        listItemNoData.add(new No_data_item("В даннной группе\n студентов нет.\n Добавьте студента."));
                        No_data_adapter NoDataAdapter = new No_data_adapter(getActivity(), R.layout.item_no_data, listItemNoData);
                        listView.setAdapter(NoDataAdapter);*/
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
                return (sendRequest.SendAndGet(socketClient, listStudents.createRequest(idGroup)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

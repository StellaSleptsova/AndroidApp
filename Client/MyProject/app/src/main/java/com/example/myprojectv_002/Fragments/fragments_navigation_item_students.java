package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.ListStudents;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter_students;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class fragments_navigation_item_students  extends Fragment{
    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter_students mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_students, container, false);
        Activity_Navigation.toolbar.setTitle("Студенты");

        mRecyclerView =(RecyclerView)v.findViewById(R.id.rv_students);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        GetListOfStudents getStudent = new GetListOfStudents();
        getStudent.execute();
        return v;
    }

    private void initListStudent(List<StudentInfo> listStudent){
        System.out.println("AAAA");
        mAdapter=new RecyclerAdapter_students(listStudent);
        System.out.println("BBBBBBBB");
        mRecyclerView.setAdapter(mAdapter);
        System.out.println("WHAT ARE ****?!");
    }

    public class GetListOfStudents extends AsyncTask<String,Void,String>{
        private Socket socketClient=null;
        private ListStudents listStudents=null;
        private SendRequest sendRequest=null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            listStudents=new ListStudents();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret){
            try{
                socketClient.close();
                System.out.println(ret.toString());
                switch (listStudents.getResponse(ret)) {
                    case "yesStudents":
                        initListStudent(listStudents.getStudents());
                        break;
                    case "noStudents":
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... data) {
            try{
                InetAddress serverAddr=InetAddress.getByName(ServerInfo.getIP());
                socketClient=new Socket(serverAddr,ServerInfo.getPort());
                System.out.println("1");
                return (sendRequest.SendAndGet(socketClient,listStudents.createRequest(UserInfo.idUser)));
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

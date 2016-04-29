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
import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.ListStudents;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter__empty;
import com.example.myprojectv_002.ResourceAdapter.RecyclerAdapter_students;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class fragments_navigation_item_students extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter_students mAdapter;
    public static ListStudents listStudents = null;
    public boolean isChange = false;
    static View v;
    public static FloatingActionButton fab_student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity_Navigation.toolbar.setTitle("Студенты");

        if (listStudents == null || isChange) {
            v = inflater.inflate(R.layout.fragment_students, container, false);

            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_students);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            isChange = false;
            GetListOfStudents getStudent = new GetListOfStudents();
            getStudent.execute();
        }
        return v;
    }

    private void initListStudent(List<StudentInfo> listStudent) {
        mAdapter = new RecyclerAdapter_students(listStudent);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEmptyCard() {
        RecyclerAdapter__empty recyclerAdapter__empty = new RecyclerAdapter__empty
                ("У вас нет студентов.\nДобавьте студента,\n нажав на  +");
        mRecyclerView.setAdapter(recyclerAdapter__empty);
    }

    public void initFab() {
        fab_student = (FloatingActionButton) v.findViewById(R.id.fab_list_student);
        fab_student.setVisibility(View.VISIBLE);
        fab_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_add_student fragment_add_student = new fragment_add_student();
                Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.clayout_list_student, fragment_add_student).addToBackStack("stack").commit();
                fab_student.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class GetListOfStudents extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            listStudents = new ListStudents();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                Activity_Navigation.progressDialog.dismiss();
                switch (listStudents.getResponse(ret)) {
                    case "yesStudents":
                        initListStudent(listStudents.getStudents());
                        break;
                    case "noStudents":
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
                System.out.println("1");
                return (sendRequest.SendAndGet(socketClient, listStudents.createRequest(UserInfo.idUser)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

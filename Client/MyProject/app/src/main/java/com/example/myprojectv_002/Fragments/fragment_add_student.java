package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.CreateRequest.AddNewStudent;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class fragment_add_student extends Fragment {
    Button buttonAddStudent;
    EditText fnameNewProblem;
    EditText snameNewProblem;
    EditText institutionStudent;
    TextView tv_warning;
    View v;
    Toast toastPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_add_student, container, false);

        Activity_Navigation.toolbar.setTitle("Добавить студента");
        RelativeLayout buttonLayoutProblem=(RelativeLayout)v.findViewById(R.id.layout_button_student_main);
        RelativeLayout buttonLayoutAddProblem=(RelativeLayout)v.findViewById(R.id.layout_button_student);
        buttonAddStudent=(Button)v.findViewById(R.id.button_addStudent);
        fnameNewProblem=(EditText)v.findViewById(R.id.editText_add_fname_student);
        snameNewProblem=(EditText)v.findViewById(R.id.editText_add_sname_student);
        institutionStudent=(EditText)v.findViewById(R.id.editText_add_institution_student);
        tv_warning=(TextView)v.findViewById(R.id.tv_warning);
        tv_warning.setVisibility(View.INVISIBLE);

        buttonLayoutAddProblem.setOnClickListener(onClickListenermain);
        buttonLayoutProblem.setOnClickListener(onClickListenermain);
        buttonAddStudent.setOnClickListener(onClickListenermain);
        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_button_student_main:
                    Activity_Navigation.fragmentManager.popBackStack();
                    fragments_navigation_item_students.fab_student.setVisibility(View.VISIBLE);
                    Activity_Navigation.toolbar.setTitle("Студенты");
                    break;
                case R.id.layout_button_student:
                    break;
                case R.id.button_addStudent:
                    buttonAddStudent.setClickable(false);
                    if (fnameNewProblem.getText().toString().equals("") || snameNewProblem.getText().toString().equals("")
                            || institutionStudent.getText().toString().equals("")) {
                        tv_warning.setVisibility(View.VISIBLE);
                        buttonAddStudent.setClickable(true);
                    } else {
                        Activity_Navigation.progressDialog.show();
                        Activity_Navigation.hideKeyBoard(v.getContext(),v);
                        addStudentTask at = new addStudentTask();
                        at.execute(fnameNewProblem.getText().toString(), snameNewProblem.getText().toString(), institutionStudent.getText().toString());
                    }
                    break;
            }
        }
    };

    public class addStudentTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest=null;
        private AddNewStudent addNewStudent=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendRequest=new SendRequest();
            addNewStudent=new AddNewStudent();
            Activity_Navigation.progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                buttonAddStudent.setClickable(true);
                Activity_Navigation.progressDialog.dismiss();
                switch (addNewStudent.getResponse(ret)) {
                    case "newStudentAdded":
                        Activity_Navigation.deleteAllFragment();
                        fragments_navigation_item_students fragment_student =new fragments_navigation_item_students();
                        fragment_student.isChange=true;
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_student).addToBackStack("stack").commit();
                        //Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, new fragments_navigation_item_groups()).commit();
                        break;
                    case "error":
                        toastPass = Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
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
                return (sendRequest.SendAndGet(socketClient, addNewStudent.createRequest(data[0],data[1],data[2], UserInfo.idUser)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

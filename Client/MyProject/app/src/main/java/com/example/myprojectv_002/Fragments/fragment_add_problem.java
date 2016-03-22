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
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.AddNewProblem;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class fragment_add_problem extends Fragment {
    Button buttonAddProblem;
    EditText nameNewProblem;
    EditText textNewProblem;
    View v;
    Toast toastPass;
    private int idGroup;

    public void setIdGroup(Integer i) {
        this.idGroup = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_add_problem, container, false);

        Activity_Navigation.toolbar.setTitle("Добавить задачу");
        RelativeLayout buttonLayoutProblem=(RelativeLayout)v.findViewById(R.id.layout_button_problem_main);
        RelativeLayout buttonLayoutAddProblem=(RelativeLayout)v.findViewById(R.id.layout_button_problem);
        buttonAddProblem=(Button)v.findViewById(R.id.button_addProblem);
        nameNewProblem=(EditText)v.findViewById(R.id.editText_add_name_problem);
        textNewProblem=(EditText)v.findViewById(R.id.editText_add_text_problem);

        buttonLayoutAddProblem.setOnClickListener(onClickListenermain);
        buttonLayoutProblem.setOnClickListener(onClickListenermain);
        buttonAddProblem.setOnClickListener(onClickListenermain);

        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_button_problem_main:
                    Activity_Navigation.fragmentManager.popBackStack();
                    break;
                case R.id.layout_button_problem:
                    break;
                case R.id.button_addProblem:
                    if (nameNewProblem.getText().toString().equals("")){
                        toastPass = Toast.makeText(getActivity(), "Введите название новой задачи", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
                        return;
                    }
                    else {
                        if(textNewProblem.getText().toString().equals("")){
                            toastPass = Toast.makeText(getActivity(), "Введите текст новой задачи", Toast.LENGTH_LONG);
                            toastPass.setGravity(Gravity.CENTER, 0, -90);
                            toastPass.show();
                            return;
                        }
                        else {
                            addProblemTask at = new addProblemTask();
                            at.execute(nameNewProblem.getText().toString(), textNewProblem.getText().toString());
                        }
                    }
                    break;
            }
        }
    };

    public class addProblemTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest=null;
        private AddNewProblem addNewProblem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendRequest=new SendRequest();
            addNewProblem=new AddNewProblem();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (addNewProblem.getResponse(ret)) {
                    case "newProblemAdded":
                        fragment_list_problems fragment_list_problems=new fragment_list_problems();
                        fragment_list_problems.setIdGroup(idGroup);
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_list_problems).addToBackStack("stack").commit();
                        toastPass = Toast.makeText(getActivity(), "Новая задача добавлена", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
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
                return (sendRequest.SendAndGet(socketClient, addNewProblem.createRequest(data[0],data[1],idGroup)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

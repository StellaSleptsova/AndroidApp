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
import com.example.myprojectv_002.CreateRequest.AddNewTask;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class fragment_add_task extends Fragment {
    Button buttonAddTask;
    EditText ed_nameTask;
    EditText ed_textTask;
    TextView tv_warning;
    View v;
    Toast toastPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_add_task, container, false);

        Activity_Navigation.toolbar.setTitle("Добавить задачу");
        RelativeLayout buttonLayoutProblem = (RelativeLayout) v.findViewById(R.id.layout_button_task_main);
        RelativeLayout buttonLayoutAddProblem = (RelativeLayout) v.findViewById(R.id.layout_button_task);
        buttonAddTask = (Button) v.findViewById(R.id.button_addTask);
        ed_nameTask = (EditText) v.findViewById(R.id.editText_add_name_task);
        ed_textTask = (EditText) v.findViewById(R.id.editText_add_text_task);
        tv_warning = (TextView) v.findViewById(R.id.tv_warning_task);
        tv_warning.setVisibility(View.INVISIBLE);

        buttonLayoutAddProblem.setOnClickListener(onClickListenermain);
        buttonLayoutProblem.setOnClickListener(onClickListenermain);
        buttonAddTask.setOnClickListener(onClickListenermain);
        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_button_task_main:
                    Activity_Navigation.fragmentManager.popBackStack();
                    fragments_navigation_item_tasks.fab_task.setVisibility(View.VISIBLE);
                    Activity_Navigation.toolbar.setTitle("Задачи");
                    break;
                case R.id.layout_button_task:
                    break;
                case R.id.button_addTask:
                    buttonAddTask.setClickable(false);
                    if (ed_nameTask.getText().toString().equals("") || ed_textTask.getText().toString().equals("")) {
                        tv_warning.setVisibility(View.VISIBLE);
                        buttonAddTask.setClickable(true);
                    } else {
                        Activity_Navigation.progressDialog.show();
                        Activity_Navigation.hideKeyBoard(v.getContext(), v);
                        addTask at = new addTask();
                        at.execute(ed_nameTask.getText().toString(), ed_textTask.getText().toString());
                    }
                    break;
            }
        }
    };

    public class addTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;
        private AddNewTask addNewTask = null;
        private String date=null;
        private Calendar calendar;
        SimpleDateFormat df;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendRequest = new SendRequest();
            addNewTask = new AddNewTask();
            calendar=Calendar.getInstance();
            df = new SimpleDateFormat("dd MMMM hh:mm");
            date=df.format(calendar.getTime());
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                buttonAddTask.setClickable(true);
                Activity_Navigation.progressDialog.dismiss();
                switch (addNewTask.getResponse(ret)) {
                    case "newTaskAdded":
                        Activity_Navigation.deleteAllFragment();
                        fragments_navigation_item_tasks fragment_task = new fragments_navigation_item_tasks();
                        fragment_task.isChange = true;
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_task).addToBackStack("stack").commit();
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
                return (sendRequest.SendAndGet(socketClient, addNewTask.createRequest(data[0], data[1],date)));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

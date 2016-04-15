package com.example.myprojectv_002.Fragment_main_activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.RequestEntry;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class Entry extends Fragment {

    private Toolbar toolbar;
    private Button button_entry;
    private EditText editText_login;
    private EditText editText_password;
    private View v;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_entry, container, false);
        initToolbar();

        button_entry=(Button)v.findViewById(R.id.button_entry);
        button_entry.setOnClickListener(onClickListenermain);

        editText_login=(EditText)v.findViewById(R.id.editText_login);
        editText_password=(EditText)v.findViewById(R.id.editText_password);

        progressBar=(ProgressBar)v.findViewById(R.id.progressBar_entry);

        return v;
    }

    private void initToolbar() {
        toolbar=(Toolbar)v.findViewById(R.id.toolbar_entry);
        toolbar.setTitle("Вход");
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_entry:
                    if(editText_login.getText().toString().equals("")){
                        Toast toastPass = Toast.makeText(getActivity(), "Введите логин", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
                    }
                    else{
                        if(editText_password.getText().toString().equals("")){
                            Toast toastPass = Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_LONG);
                            toastPass.setGravity(Gravity.CENTER, 0, -90);
                            toastPass.show();
                        }
                        else{
                            //Intent intentEntry = new Intent(getActivity(), Activity_Navigation.class);
                            //getActivity().startActivity(intentEntry);
                            EntryTask entryTask = new EntryTask();
                            entryTask.execute(editText_login.getText().toString(),editText_password.getText().toString());
                        }
                    }
            }
        }
    };

    public class EntryTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private RequestEntry requestEntry;
        SendRequest sendRequest;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            requestEntry=new RequestEntry();
            sendRequest=new SendRequest();
            button_entry.setEnabled(false);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch(requestEntry.getResponse(ret)) {
                    case "ok":
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Intent intentEntry = new Intent(getActivity(), Activity_Navigation.class);
                        getActivity().startActivity(intentEntry);
                        break;
                    case "incorrect password":
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Toast toastPass = Toast.makeText(getActivity(), "Неправильный пароль", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
                        button_entry.setEnabled(true);
                        break;
                    case "incorrect login":
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Toast toastLogin = Toast.makeText(getActivity(), "Неправильный логин", Toast.LENGTH_LONG);
                        toastLogin.setGravity(Gravity.CENTER, 0, -90);
                        toastLogin.show();
                        button_entry.setEnabled(true);
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
                return (sendRequest.SendAndGet(socketClient,requestEntry.createRequest(data[0],data[1])));
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}


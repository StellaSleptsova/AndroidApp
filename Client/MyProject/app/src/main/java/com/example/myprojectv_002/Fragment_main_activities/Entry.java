package com.example.myprojectv_002.Fragment_main_activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.DialogFragment;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.RequestEntry;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.MainActivity;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class Entry extends Fragment {

    private Button button_entry;
    private Button button_reg;
    private EditText editText_login;
    private EditText editText_password;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_entry, container, false);

        button_entry=(Button)v.findViewById(R.id.button_entry_main);
        button_reg=(Button)v.findViewById(R.id.button_reg_main);
        button_reg.setOnClickListener(onClickListenermain);
        button_entry.setOnClickListener(onClickListenermain);

        editText_login=(EditText)v.findViewById(R.id.et_login_entry);
        editText_password=(EditText)v.findViewById(R.id.et_password_entry);

        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_entry_main:
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
                            MainActivity.hideKeyBoard(v.getContext(), v);
                            EntryTask entryTask = new EntryTask();
                            entryTask.execute(editText_login.getText().toString(),editText_password.getText().toString());
                        }
                    }
                    break;
                case R.id.button_reg_main:
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.activity_main, new Registration()).addToBackStack("main_stack").commit();
                    break;
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
            MainActivity.progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                MainActivity.progressDialog.dismiss();
                switch(requestEntry.getResponse(ret)) {
                    case "ok":
                        Intent intentEntry = new Intent(getActivity(), Activity_Navigation.class);
                        getActivity().startActivity(intentEntry);
                        break;
                    case "incorrect":
                        DialogFragment dialogFragment= new DialogFragment("Ошибка авторизации", "При входе произошла ошибка.\nПроверьте введенные данные и\n попробуйте еще раз.");
                        dialogFragment.show(getFragmentManager(),"mylog_dialogFragment");
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


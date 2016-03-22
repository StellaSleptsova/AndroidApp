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
import com.example.myprojectv_002.CreateRequest.AddNewGroup;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class fragment_add_group extends Fragment {

    Button buttonAddGroup;
    EditText editText_nameGroup;
    View v;
    Toast toastPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_add_group, container, false);

        Activity_Navigation.toolbar.setTitle("Добавить группу");
        RelativeLayout buttonLayoutGroup = (RelativeLayout) v.findViewById(R.id.layout_button_group_main);
        RelativeLayout buttonLayoutAddGroup = (RelativeLayout) v.findViewById(R.id.layout_button_group);
        buttonAddGroup = (Button) v.findViewById(R.id.button_addGroup);
        editText_nameGroup = (EditText) v.findViewById(R.id.editText_add_name_group);

        buttonLayoutAddGroup.setOnClickListener(onClickListenermain);
        buttonLayoutGroup.setOnClickListener(onClickListenermain);
        buttonAddGroup.setOnClickListener(onClickListenermain);
        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_button_group_main:
                    Activity_Navigation.fragmentManager.popBackStack();
                    break;
                case R.id.layout_button_group:
                    break;
                case R.id.button_addGroup:
                    if (editText_nameGroup.getText().toString().equals("")){
                        Toast toastPass = Toast.makeText(getActivity(), "Введите название новой группы", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
                        return;
                    }
                    else {
                        addGroupTask at = new addGroupTask();
                        at.execute(editText_nameGroup.getText().toString());
                    }
                    break;
            }
        }
    };

    public class addGroupTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest=null;
        private AddNewGroup addNewGroup=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendRequest=new SendRequest();
            addNewGroup=new AddNewGroup();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (addNewGroup.getResponse(ret)) {
                    case "newGroupAdded":
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, new fragments_navigation_item_groups()).addToBackStack("stack").commit();
                        toastPass = Toast.makeText(getActivity(), "Новая группа добавлена", Toast.LENGTH_LONG);
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
                return (sendRequest.SendAndGet(socketClient, addNewGroup.createRequest(data[0])));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

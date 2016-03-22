package com.example.myprojectv_002.Fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.DeletedObject;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class fragment_menu extends Fragment {

    private View v;
    private String nameAction;
    private Toast toastLogin;
    private int idObject;
    private int idGroup;

    public void setId(int i) {
        this.idObject = i;
    }
    public void setIdGroup(int i){this.idGroup=i;}

    public void setNameAction(String nameAction) {
        this.nameAction = nameAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_menu, container, false);
        RelativeLayout buttonLayout = (RelativeLayout) v.findViewById(R.id.layout_menu);
        buttonLayout.setOnClickListener(onClickListenermain);

        ListView listView = (ListView) v.findViewById(R.id.list_menu);
        String[] valuesMenu = new String[]{"Изменить название", "Удалить"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_menu, R.id.text_menu, valuesMenu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getActivity(), "На стадии разработки", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        openQuitDialog();
                        break;
                }
            }
        });
        return v;
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(getActivity());
        quitDialog.setTitle("Удалить " + "<" + nameAction + ">" + "?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuTask at = new MenuTask();
                at.execute("delete");
            }
        });

        quitDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quitDialog.getContext();
            }
        });

        quitDialog.show();
    }


    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_menu:
                    Activity_Navigation.fragmentManager.popBackStack();
                    break;
            }
        }
    };

    public class MenuTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;
        private DeletedObject deletedObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendRequest = new SendRequest();
            deletedObject = new DeletedObject();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (deletedObject.getResponse(ret)) {
                    case "groupDeleted":
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, new fragments_navigation_item_groups()).addToBackStack("stack").commit();
                        toastLogin = Toast.makeText(getActivity(), "Группа удалена", Toast.LENGTH_LONG);
                        toastLogin.setGravity(Gravity.TOP, 0, -90);
                        toastLogin.show();
                        break;
                    case "problemDeleted":
                        fragment_list_problems fragment_list_problems=new fragment_list_problems();
                        fragment_list_problems.setIdGroup(idGroup);
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_list_problems).addToBackStack("stack").commit();
                        toastLogin = Toast.makeText(getActivity(), "Задача удалена", Toast.LENGTH_LONG);
                        toastLogin.setGravity(Gravity.TOP, 0, -90);
                        toastLogin.show();
                        break;
                    case "studentDeleted":
                        fragment_list_student fragment_list_student=new fragment_list_student();
                        fragment_list_student.setIdGroup(idGroup);
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_list_student).addToBackStack("stack").commit();
                        toastLogin = Toast.makeText(getActivity(), "Студент удален", Toast.LENGTH_LONG);
                        toastLogin.setGravity(Gravity.TOP, 0, -90);
                        toastLogin.show();
                        break;
                    default:
                        Activity_Navigation.fragmentManager.popBackStack();
                        toastLogin = Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG);
                        toastLogin.setGravity(Gravity.TOP, 0, -90);
                        toastLogin.show();
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
                if (data[0].equals("delete")) {
                    return (sendRequest.SendAndGet(socketClient, deletedObject.createRequest(nameAction, idObject,idGroup)));
                } else {
                    //return (sendRequest.SendAndGet(socketClient, deleteGroup.createRequest(id)));
                    return "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

package com.example.myprojectv_002.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.DeletedObject;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class PopUpMenuEventHandle implements PopupMenu.OnMenuItemClickListener{
    Context context;
    String nameOfRequest;
    int idObject;

    public PopUpMenuEventHandle(Context context, String nameOfRequest, int idObject){
        this.nameOfRequest=nameOfRequest;
        this.context=context;
        this.idObject=idObject;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_delete:
                openQuitDialog();
                break;
            case R.id.menu_item_change:
                Toast.makeText(context, "change", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(context);
        quitDialog.setTitle("Удалить "  + Activity_Navigation.mapTranslate.get(nameOfRequest)  + "?");

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

    public class MenuTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;
        private DeletedObject deletedObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Navigation.progressDialog.show();
            sendRequest = new SendRequest();
            deletedObject = new DeletedObject();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                Activity_Navigation.progressDialog.dismiss();
                System.out.println(ret);
                switch (deletedObject.getResponse(ret)) {
                    case "groupDeleted":
                        fragments_navigation_item_groups fragment_group =new fragments_navigation_item_groups();
                        fragment_group.isChange=true;
                        Activity_Navigation.deleteAllFragment();
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_group).addToBackStack("stack").commit();
                        break;
                    case "taskDeleted":
                        fragments_navigation_item_tasks fragment_task =new fragments_navigation_item_tasks();
                        fragment_task.isChange=true;
                        Activity_Navigation.deleteAllFragment();
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_task).addToBackStack("stack").commit();
                        break;
                    case "studentDeleted":
                        fragments_navigation_item_students fragment_student =new fragments_navigation_item_students();
                        fragment_student.isChange=true;
                        Activity_Navigation.deleteAllFragment();
                        Activity_Navigation.fragmentManager.beginTransaction().replace(R.id.main_content, fragment_student).addToBackStack("stack").commit();
                        break;
                    default:
                        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
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
                    return (sendRequest.SendAndGet(socketClient, deletedObject.createRequest(nameOfRequest, idObject)));
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

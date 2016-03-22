package com.example.myprojectv_002.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.CreateRequest.TextProblem;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;

public class fragment_text_problem extends Fragment {

    TextView nameProblem;
    TextView textProblem;
    Integer idProblem;
    View v;

    public void setIdProblem(Integer i){
        this.idProblem=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_text_problem, container, false);

        Activity_Navigation.toolbar.setTitle("Информация о задаче");
        nameProblem = (TextView) v.findViewById(R.id.text_text_title_problem);
        textProblem=(TextView)v.findViewById(R.id.text_text_subtitle_problem);

        TextProblemTask at = new TextProblemTask();
        at.execute();
        return v;
    }

    public class TextProblemTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private SendRequest sendRequest = null;
        private TextProblem textProblemReq=null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            sendRequest = new SendRequest();
            textProblemReq=new TextProblem();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                switch (textProblemReq.getResponse(ret)) {
                    case "ok":
                        nameProblem.setText(textProblemReq.getNameProblem());
                        textProblem.setText(textProblemReq.getTextProblem());
                        break;
                    case "error":
                        //ERROR
                        socketClient.close();
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
                return (sendRequest.SendAndGet(socketClient, textProblemReq.createRequest(idProblem)));
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

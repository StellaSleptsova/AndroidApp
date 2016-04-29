package com.example.myprojectv_002.Fragment_main_activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprojectv_002.ClassesObject.DialogFragment;
import com.example.myprojectv_002.ClassesObject.ServerInfo;
import com.example.myprojectv_002.CreateRequest.RequestRegistration;
import com.example.myprojectv_002.CreateRequest.SendRequest;
import com.example.myprojectv_002.MainActivity;
import com.example.myprojectv_002.R;

import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Fragment {

    private Toolbar toolbar;
    private EditText et_fname;
    private EditText et_sname;
    private EditText et_institution;
    private EditText et_login;
    private EditText et_password;
    private EditText et_repeate_password;
    private Button button_reg;
    private TextView tv_warning;
    private static final String loginPattern = "^([A-Za-z0-9]{1,50})$";
    private static final String passPattern = "^([A-Za-z0-9]{1,50})$";
    private static final String namePattern ="^([A-Za-zА-Яа-я0-9]{1,50})$";
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_registration, container, false);
        initToolbar();

        et_fname = (EditText) v.findViewById(R.id.editText_reg_first_name);
        et_sname = (EditText) v.findViewById(R.id.editText_reg_second_name);
        et_institution = (EditText) v.findViewById(R.id.editText_reg_institution);
        et_login = (EditText) v.findViewById(R.id.editText_reg_login);
        et_password = (EditText) v.findViewById(R.id.editText_reg_password);
        et_repeate_password = (EditText) v.findViewById(R.id.editText_reg_password_repeate);
        tv_warning = (TextView) v.findViewById(R.id.tv_warning_reg);
        tv_warning.setVisibility(View.INVISIBLE);
        button_reg = (Button) v.findViewById(R.id.button_reg);
        button_reg.setOnClickListener(onClickListenermain);

        return v;
    }

    private void initToolbar() {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar_reg);
        toolbar.setTitle("Регистрация");
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_reg:
                    button_reg.setClickable(false);
                    MainActivity.hideKeyBoard(v.getContext(), v);
                    if (et_fname.getText().toString().equals("") || et_sname.getText().toString().equals("") ||
                            et_institution.getText().toString().equals("") || et_login.getText().toString().equals("") ||
                            et_password.getText().toString().equals("") || et_repeate_password.getText().toString().equals("")) {
                        tv_warning.setVisibility(View.VISIBLE);
                        button_reg.setClickable(true);
                    } else {
                        if ( lengthStr() && checkEditText()) {
                            MainActivity.progressDialog.show();
                            ReqTask reqTask = new ReqTask();
                            reqTask.execute(et_fname.getText().toString(), et_sname.getText().toString(), et_institution.getText().toString(),
                                    et_login.getText().toString(), et_password.getText().toString());
                        }else{
                            button_reg.setClickable(true);
                        }
                    }
                    break;
            }
        }
    };

    private boolean passwordValidate(String str) {
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean loginValidate(String str) {
        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean nameValidate(String str) {
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean lengthStr() {
        if (et_login.getText().toString().length() < 6) {
            et_login.setError("Недопустимое количество символов");
            return false;
        }
        if(et_password.getText().toString().length()<6){
            et_password.setError("Недопустимое колчиество символов");
            return false;
        }
        return true;
    }

    private boolean checkEditText() {
        if (nameValidate(et_fname.getText().toString()))
            if (nameValidate(et_sname.getText().toString()))
                if (nameValidate(et_institution.getText().toString()))
                    if (loginValidate(et_login.getText().toString()))
                        if (passwordValidate(et_password.getText().toString()))
                            if (et_repeate_password.getText().toString().equals(et_password.getText().toString()))
                                return true;
                            else {
                                et_repeate_password.setError("Неверный пароль");
                                return false;
                            }
                        else {
                            et_password.setError("Недопустимые символы");
                            return false;
                        }
                    else {
                        et_login.setError("Недопустимые символы");
                        return false;
                    }
                else {
                    et_institution.setError("Недопустимые символы");
                    return false;
                }
            else {
                et_sname.setError("Недопустимые символы");
                return false;
            }
        else {
            et_fname.setError("Недопустимые символы");
            return false;
        }
    }

    public class ReqTask extends AsyncTask<String, Void, String> {
        private Socket socketClient = null;
        private RequestRegistration requestRegistration;
        SendRequest sendRequest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestRegistration = new RequestRegistration();
            sendRequest = new SendRequest();
        }

        @Override
        protected void onPostExecute(final String ret) {
            try {
                socketClient.close();
                System.out.println(ret);
                MainActivity.progressDialog.dismiss();
                switch (requestRegistration.getResponse(ret)) {
                    case "ok":
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.activity_main,new Entry()).addToBackStack("main_stack").commit();
                        DialogFragment dialogFragment = new DialogFragment("Регистрация прошла успешно.", "");
                        dialogFragment.show(getFragmentManager(), "mylog_dialogFragment");
                        break;
                    case "incorrect":
                        Toast toastPass = Toast.makeText(getActivity(), "Такой логин уже существует", Toast.LENGTH_LONG);
                        toastPass.setGravity(Gravity.CENTER, 0, -90);
                        toastPass.show();
                        button_reg.setClickable(true);
                        break;
                    case "error":
                        Toast toast = Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, -90);
                        toast.show();
                        button_reg.setClickable(true);
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
                return (sendRequest.SendAndGet(socketClient, requestRegistration.createRequest(data[0], data[1],data[2],data[3],data[4])));
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }
}

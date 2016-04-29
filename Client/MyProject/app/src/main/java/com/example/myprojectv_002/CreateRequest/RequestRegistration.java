package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.UserInfo;

import org.json.JSONObject;

public class RequestRegistration {

    public RequestRegistration(){
    }

    public String createRequest(String fname, String sname, String institution, String login, String pass){
        try{
            JSONObject object=new JSONObject();
            JSONObject dataUser=new JSONObject();
            dataUser.put("fname",fname);
            dataUser.put("sname",sname);
            dataUser.put("institution",institution);
            dataUser.put("login",login);
            dataUser.put("pass",pass);
            dataUser.put("user",UserInfo.student_or_teacher);
            object.put("req","registration");
            object.put("dataUser",dataUser);
            return object.toString();
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getResponse(String str){
        try {
            JSONObject retJson=new JSONObject(str);
            String response=retJson.get("answer").toString();
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }
}

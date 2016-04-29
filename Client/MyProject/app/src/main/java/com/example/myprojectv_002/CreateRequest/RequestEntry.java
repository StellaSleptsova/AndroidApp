package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.UserInfo;

import org.json.JSONObject;

public class RequestEntry {

    public RequestEntry() {
    }

    public String createRequest(String login,String pass) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataUser = new JSONObject();
            dataUser.put("login", login);
            dataUser.put("password", pass);
            dataUser.put("user", UserInfo.student_or_teacher);
            object.put("req", "entry");
            object.put("dataUser", dataUser);
            return(object.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getResponse(String str){
        try {
            JSONObject retJson = new JSONObject(str);
            String response = retJson.get("answerUser").toString();
            if(response.equals("ok")){
                UserInfo.setUserInfo(retJson);
            }
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

}

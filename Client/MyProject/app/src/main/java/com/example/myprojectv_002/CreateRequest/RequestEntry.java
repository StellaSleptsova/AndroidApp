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
                setUserInfo(retJson);
            }
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    private void setUserInfo(JSONObject ret){
        try {
            UserInfo.nameUser = ret.get("namef").toString() + " " + ret.get("names").toString();
            UserInfo.idUser = Integer.parseInt(ret.get("idUser").toString());
            UserInfo.loginUser=ret.get("login").toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}

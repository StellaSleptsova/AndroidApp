package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.UserInfo;

import org.json.JSONObject;

public class AddNewTask {

    public AddNewTask() {
    }

    public String createRequest(String name, String text,String date) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("nameNewTask", name);
            dataReq.put("textNewTask", text);
            dataReq.put("dateNewTask", date);
            dataReq.put("idTeacher", UserInfo.idUser);
            object.put("req", "addNewTask");
            object.put("dataReq", dataReq);
            return (object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getResponse(String str) {
        try {
            JSONObject retJson = new JSONObject(str);
            return retJson.get("answer").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}

package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.UserInfo;

import org.json.JSONObject;

public class AddNewGroup {

    public AddNewGroup() {
    }

    public String createRequest(String str) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("nameNewGroup", str);
            dataReq.put("idTeacher", UserInfo.idUser.toString());
            object.put("req", "addNewGroup");
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

package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.UserInfo;

import org.json.JSONObject;

public class DeletedObject {

    public DeletedObject() {
    }

    public String createRequest(String objectDelete,Integer idObject) {
        try {
            JSONObject object=new JSONObject();
            JSONObject dataReq=new JSONObject();
            dataReq.put("idObject", idObject);
            dataReq.put("idTeacher", UserInfo.idUser);
            object.put("req","delete"+objectDelete);
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

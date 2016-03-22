package com.example.myprojectv_002.CreateRequest;

import org.json.JSONObject;

/**
 * Created by Пользователь on 14.12.2015.
 */
public class DeletedObject {

    public DeletedObject() {
    }

    public String createRequest(String objectDelete,Integer idObject,Integer idGroup) {
        try {
            JSONObject object=new JSONObject();
            JSONObject dataReq=new JSONObject();
            dataReq.put("idObject", idObject);
            dataReq.put("idGroup",idGroup);
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

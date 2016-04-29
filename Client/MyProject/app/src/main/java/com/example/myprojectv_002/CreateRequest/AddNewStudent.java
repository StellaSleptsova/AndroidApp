package com.example.myprojectv_002.CreateRequest;

import org.json.JSONObject;

public class AddNewStudent {

    public AddNewStudent() {
    }

    public String createRequest(String fname, String sname,String inst, Integer idT) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("fnameNewStudent", fname);
            dataReq.put("snameNewStudent", sname);
            dataReq.put("institution",inst );
            dataReq.put("idTeacher", idT);
            object.put("req", "addNewStudent");
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

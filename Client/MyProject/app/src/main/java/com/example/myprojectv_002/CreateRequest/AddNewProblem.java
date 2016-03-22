package com.example.myprojectv_002.CreateRequest;

import org.json.JSONObject;

public class AddNewProblem {

    public AddNewProblem() {
    }

    public String createRequest(String name, String text, Integer idG) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("nameNewProblem", name);
            dataReq.put("textNewProblem", text);
            dataReq.put("idGroup", idG);
            object.put("req", "addNewProblem");
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

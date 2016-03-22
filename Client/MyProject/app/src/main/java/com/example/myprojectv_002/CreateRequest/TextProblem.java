package com.example.myprojectv_002.CreateRequest;

import org.json.JSONObject;

public class TextProblem {
    public TextProblem() {
    }

    private String nameProblem=null;
    private String textProblem=null;

    public String createRequest(Integer id) {
        try {
            JSONObject object=new JSONObject();
            JSONObject dataReq=new JSONObject();
            dataReq.put("idProblem", id);
            object.put("req","getTextProblem");
            object.put("dataReq", dataReq);
            String strRequest=object.toString();
            return (object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getResponse(String str) {
        try {
            JSONObject retJson = new JSONObject(str);
            String response = retJson.get("answer").toString();
            if (response.equals("ok")) {
                JSONObject dataProblem=new JSONObject(retJson.getString("dataProblem"));
                createNameProblem(dataProblem);
                createTextProblem(dataProblem);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public void createNameProblem(JSONObject dataProblem) {
        try {
            nameProblem=dataProblem.getString("nameProblem");
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void createTextProblem(JSONObject dataProblem) {
        try {
            textProblem=dataProblem.getString("textProblem");
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    public String getNameProblem(){
        return nameProblem;
    }

    public String getTextProblem(){
        return textProblem;
    }
}

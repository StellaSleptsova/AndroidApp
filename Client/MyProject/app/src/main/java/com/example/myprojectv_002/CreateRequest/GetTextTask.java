package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.TaskInfo;

import org.json.JSONObject;

public class GetTextTask {
    public GetTextTask() {
    }

    public String createRequest(Integer id) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("idTask", id);
            object.put("req", "getTextTask");
            object.put("dataReq", dataReq);
            System.out.println(object.toString());
            return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getResponse(String str,TaskInfo taskInfo) {
        try {
            JSONObject retJson = new JSONObject(str);
            String response = retJson.get("answer").toString();
            if (response.equals("ok")) {
                JSONObject dataProblem = new JSONObject(retJson.getString("dataTask"));
                taskInfo.setTextTask(dataProblem);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}

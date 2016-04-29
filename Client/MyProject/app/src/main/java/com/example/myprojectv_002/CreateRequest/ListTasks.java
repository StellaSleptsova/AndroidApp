package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.TaskInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListTasks {

    public ListTasks() {
    }

    private List<TaskInfo> tasks=null;

    public String createRequest(Integer idGroup) {
        try {
            JSONObject object=new JSONObject();
            JSONObject dataUser=new JSONObject();
            dataUser.put("idTeacher", idGroup);
            object.put("req","listTasks");
            object.put("dataReq", dataUser);
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
            if (response.equals("yesTasks")) {
                JSONArray jsonArrayInfoGroup = new JSONArray(retJson.get("infoTasks").toString());
                createProblems(jsonArrayInfoGroup);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public void createProblems(JSONArray obj)
    {
        try {
            tasks = new ArrayList<TaskInfo>();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jsonObject = obj.getJSONObject(i);
                System.out.println(jsonObject.toString());
                tasks.add(new TaskInfo(jsonObject));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<TaskInfo> getTasks()
    {
        return tasks;
    }
}

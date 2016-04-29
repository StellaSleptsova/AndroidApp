package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.StudentInfo;
import com.example.myprojectv_002.ClassesObject.TaskInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GetList_forGroup {

    List<StudentInfo> listStudents=null;
    List<TaskInfo> listTasks=null;

    public void setListStudents(List<StudentInfo> ls) {
        listStudents=ls;
    }

    public void setListTasks(List<TaskInfo> lt){
        listTasks=lt;
    }

    public String createRequest(String id, String str_obj) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("idGroup", id);
            object.put("req", "getList"+ str_obj+"_forGroup");
            object.put("dataReq", dataReq);
            System.out.println(object.toString());
            return object.toString();
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
                JSONArray dataList = new JSONArray(retJson.getString("dataList"));
                if(listStudents!=null) {
                    for (int i = 0; i < dataList.length(); i++) {
                        StudentInfo studentInfo=new StudentInfo();
                        studentInfo.setInfoStudent_for_groups(dataList.getJSONObject(i));
                        listStudents.add(studentInfo);
                    }
                }else{
                    for (int i = 0; i < dataList.length(); i++) {
                        TaskInfo taskInfo=new TaskInfo();
                        taskInfo.setInfoTask_forGroup(dataList.getJSONObject(i));
                        listTasks.add(taskInfo);
                    }
                }
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}

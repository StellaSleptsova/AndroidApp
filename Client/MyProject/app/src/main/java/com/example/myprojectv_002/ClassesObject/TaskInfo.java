package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskInfo {
    public int idTask;
    public String nameTask;
    public String textTask;
    public String dateTask;

    public TaskInfo(){}
    public TaskInfo(JSONObject objRet) throws JSONException {
        idTask = Integer.parseInt(objRet.get("idTask").toString());
        nameTask = objRet.get("nameTask").toString();
        dateTask=objRet.get("dateTask").toString();
    }
    public void setTextTask(JSONObject objRet) throws JSONException{
        textTask=objRet.get("textTask").toString();
    }

    public void setInfoTask_forGroup(JSONObject object) throws JSONException{
        idTask = Integer.parseInt(object.get("idTask").toString());
        nameTask = object.get("nameTask").toString();
    }
}

package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentInfo {
    public Integer idStudent;
    public String nameStudent;
    public String institution;
    public int count_solvedTask;
    public int count_unsolvedTask;

    public StudentInfo(JSONObject objRet) throws JSONException {
        idStudent = Integer.parseInt(objRet.get("idStudent").toString());
        nameStudent = objRet.get("fnameStudent").toString() + " " + objRet.get("snameStudent").toString();
        institution = objRet.get("institution").toString();
    }

    public StudentInfo(){}
    public void setInfoStudent_for_groups(JSONObject object)throws JSONException {
        idStudent = Integer.parseInt(object.get("idStudent").toString());
        nameStudent = object.get("fnameStudent").toString() + " " + object.get("snameStudent").toString();
        count_solvedTask=Integer.parseInt(object.get("countSolvedTask").toString());
        count_unsolvedTask=Integer.parseInt(object.get("countUnsolvedTask").toString());
    }
}

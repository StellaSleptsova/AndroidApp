package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupInfo {
    public int idGroup;
    public String nameGroup;
    public String institution;
    public Integer countStudents;
    public Integer countProblems;

    public GroupInfo(JSONObject objRet) throws JSONException {
        idGroup = Integer.parseInt(objRet.get("idGroup").toString());
        nameGroup = objRet.get("nameGroup").toString();
        countStudents=objRet.getInt("countStudents");
        countProblems=objRet.getInt("countProblems");
        institution=objRet.get("institution").toString();
    }
}

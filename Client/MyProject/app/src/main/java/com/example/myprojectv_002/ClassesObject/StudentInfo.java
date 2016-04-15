package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Пользователь on 12.12.2015.
 */
public class StudentInfo {
    public int idStudent;
    public String nameStudent;
    public String institution;

    public StudentInfo(JSONObject objRet) throws JSONException {
        idStudent = Integer.parseInt(objRet.get("idStudent").toString());
        nameStudent = objRet.get("fnameStudent").toString() + " " + objRet.get("snameStudent").toString();
        institution = objRet.get("institution").toString();
    }
}

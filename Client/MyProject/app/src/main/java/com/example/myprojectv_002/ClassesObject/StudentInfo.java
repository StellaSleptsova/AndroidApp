package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Пользователь on 12.12.2015.
 */
public class StudentInfo {
    public int idStudent;
    public String nameStudent;
    public Integer countSolveProblems;
    public Integer countProblem;

    public StudentInfo(JSONObject objRet) throws JSONException {
        idStudent = Integer.parseInt(objRet.get("idStudent").toString());
        nameStudent = objRet.get("nameStudent").toString();
        //countSolveProblems=Integer.parseInt(objRet.get("countSolveProblems").toString());
        countSolveProblems=Integer.parseInt("12");
    }
}

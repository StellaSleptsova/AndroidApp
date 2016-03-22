package com.example.myprojectv_002.ClassesObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Пользователь on 12.12.2015.
 */
public class ProblemInfo {
    public int idProblem;
    public String nameProblem;
    public String starTextProblem;
    //public String dateProblem;

    public ProblemInfo(JSONObject objRet) throws JSONException {
        idProblem = Integer.parseInt(objRet.get("idProblem").toString());
        nameProblem = objRet.get("nameProblem").toString();
        starTextProblem=objRet.getString("textProblem");
        //dateProblem=objRet.get("dateProblem").toString();
    }
}

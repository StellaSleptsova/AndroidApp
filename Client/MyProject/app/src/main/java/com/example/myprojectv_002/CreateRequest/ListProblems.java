package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.ProblemInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Пользователь on 12.12.2015.
 */
public class ListProblems {

    public ListProblems() {
    }

    private List<ProblemInfo> problems=null;

    public String createRequest(Integer idGroup) {
        try {
            JSONObject object=new JSONObject();
            JSONObject dataUser=new JSONObject();
            dataUser.put("idGroup", idGroup);
            object.put("req","listProblems");
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
            if (response.equals("yesProblems")) {
                JSONArray jsonArrayInfoGroup = new JSONArray(retJson.get("infoProblems").toString());
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
            problems = new ArrayList<ProblemInfo>();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jsonObject = obj.getJSONObject(i);
                System.out.println(jsonObject.toString());
                problems.add(new ProblemInfo(jsonObject));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<ProblemInfo> getProblems()
    {
        return problems;
    }
}

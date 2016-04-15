package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.StudentInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListStudents {

    public ListStudents() {
    }

    private List<StudentInfo> students=null;

    public String createRequest(Integer idTeacher) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataUser = new JSONObject();
            dataUser.put("idTeacher", idTeacher);
            object.put("req", "list_of_students");
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
            if (response.equals("yesStudents")) {
                JSONArray jsonArrayInfoGroup = new JSONArray(retJson.get("infoStudents").toString());
                createStudents(jsonArrayInfoGroup);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public  void createStudents(JSONArray obj) {
        try {
            students = new ArrayList<StudentInfo>();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jsonObject = obj.getJSONObject(i);
                System.out.println(jsonObject.toString());
                students.add(new StudentInfo(jsonObject));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<StudentInfo> getStudents()
    {
        return students;
    }
}

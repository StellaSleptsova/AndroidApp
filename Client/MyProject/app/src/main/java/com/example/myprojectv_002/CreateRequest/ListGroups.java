package com.example.myprojectv_002.CreateRequest;

import com.example.myprojectv_002.ClassesObject.GroupInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListGroups {

    public ListGroups() {
    }

    private List<GroupInfo> groups = null;

    public String createRequest(Integer id) {
        try {
            JSONObject object = new JSONObject();
            JSONObject dataReq = new JSONObject();
            dataReq.put("id", id);
            object.put("req", "listGroup");
            object.put("dataUser", dataReq);
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
            if (response.equals("YesGroups")) {
                JSONArray jsonArrayInfoGroup = new JSONArray(retJson.get("infoGroup").toString());
                createGroups(jsonArrayInfoGroup);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public void createGroups(JSONArray obj) {
        try {
            groups = new ArrayList<GroupInfo>();
            for (int i = 0; i < obj.length(); i++) {
                groups.add(new GroupInfo(obj.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GroupInfo> getGroups(){
        return groups;
    }
}

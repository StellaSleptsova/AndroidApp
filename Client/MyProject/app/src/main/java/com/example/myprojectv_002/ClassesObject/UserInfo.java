package com.example.myprojectv_002.ClassesObject;

import org.json.JSONObject;

public class UserInfo {
    public static int student_or_teacher;
    public static Integer idUser;
    public static String nameUser;
    public static String loginUser;
    public static String institution;


    public static void setUserInfo(JSONObject ret){
        try {
            UserInfo.nameUser = ret.get("fname").toString() + " " + ret.get("sname").toString();
            UserInfo.idUser = Integer.parseInt(ret.get("idUser").toString());
            UserInfo.loginUser=ret.get("login").toString();
            UserInfo.institution=ret.get("institution").toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}

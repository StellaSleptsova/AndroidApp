package com.example.myprojectv_002.ResourceItem;

/**
 * Created by Пользователь on 28.11.2015.
 */
public class Group_item {
    private String Title;
    private String countStudent;
    private String countProblems;

    public Group_item(String title, String countStudent, String countProblems) {
        super();
        this.Title=title;
        this.countStudent=countStudent;
        this.countProblems=countProblems;
    }

    public String getTitle(){
        return Title;
    }

    public void  setTitle(String title) {
        this.Title = title;
    }

    public String getCountStudent()
    {
        return  countStudent;
    }
    public void setCountStudent(String dial)
    {
        this.countStudent=dial;
    }
    public String getCountProblems(){return  countProblems;}
    public void setCountProblems(String cp){this.countProblems=cp;}

}

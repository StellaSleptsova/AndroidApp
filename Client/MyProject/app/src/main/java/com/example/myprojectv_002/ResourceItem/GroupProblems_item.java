package com.example.myprojectv_002.ResourceItem;

/**
 * Created by Пользователь on 04.12.2015.
 */
public class GroupProblems_item {
    private String Title;
    private String countProblems;

    public GroupProblems_item(String title, String countProblems) {
        super();
        this.Title=title;
        this.countProblems=countProblems;
    }

    public String getTitle(){
        return Title;
    }

    public void  setTitle(String title) {
        this.Title = title;
    }
    public String getCountProblems(){return  countProblems;}
    public void setCountProblems(String cp){this.countProblems=cp;}
}

package com.example.myprojectv_002.ResourceItem;

/**
 * Created by Пользователь on 06.12.2015.
 */
public class Problem_item {
    private String nameProblem;
    private String startTextProblem;

    public Problem_item(String title, String countProblems) {
        super();
        this.nameProblem=title;
        this.startTextProblem=countProblems;
    }

    public String getNameProblem(){
        return nameProblem;
    }

    public void  setNameProblem(String title) {
        this.nameProblem = title;
    }
    public String getStartTextProblem(){return  startTextProblem;}
    public void setStartTextProblem(String cp){this.startTextProblem=cp;}
}

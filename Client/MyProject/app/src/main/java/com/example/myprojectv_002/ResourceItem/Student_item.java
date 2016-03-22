package com.example.myprojectv_002.ResourceItem;

/**
 * Created by Пользователь on 28.11.2015.
 */
public class Student_item {
    private String Name;
    private Integer CountSolveProblems;
    private Integer CountProblems;

    public Student_item(String name, Integer solveProblems,Integer problems) {
        super();
        this.Name=name;
        this.CountSolveProblems=solveProblems;
        this.CountProblems=problems;
    }

    public String getName(){
        return Name;
    }

    public void  setName(String title) {
        this.Name = title;
    }

    public Integer getCountSolveProblems()
    {
        return  CountSolveProblems;
    }
    public void setCountSolveProblems(Integer dial)
    {
        this.CountProblems=dial;
    }
    public Integer getCountProblems()
    {
        return  CountProblems;
    }
    public void setCountProblems(Integer dial)
    {
        this.CountProblems=dial;
    }
}

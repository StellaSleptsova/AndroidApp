package com.example.myprojectv_002.ResourceItem;

public class ListOfGroupForStudent_item {
    private String nameGroup;
    private int solveTasks;
    private int unSolveTasks;

    public ListOfGroupForStudent_item(String name_group, int sT, int unSt) {
        this.nameGroup = name_group;
        this.solveTasks = sT;
        this.unSolveTasks = unSt;
    }

    public String getNameGroup(){return nameGroup;}
    public int getSolveTasks(){return solveTasks;}
    public int getUnSolveTasks(){return unSolveTasks;}

    public void setNameGroup(String nameG){nameGroup=nameG;}
    public void setSolveTasks(int i){solveTasks=i;}
    public void setUnSolveTasks(int i){unSolveTasks=i;}
}

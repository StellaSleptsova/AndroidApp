package com.example.myprojectv_002.ResourceItem;

public class Navigation_Item {
    private String title;
    private int resIcon;

    public Navigation_Item(String title, int resIcon) {
        super();
        this.title=title;
        this.resIcon=resIcon;
    }

    public String getTitle(){
        return title;
    }

    public void  setTitle(String title) {
        this.title = title;
    }

    public int getResIcon(){
        return  resIcon;
    }

    public void setResIcon(int resIcon){
        this.resIcon=resIcon;
    }
}

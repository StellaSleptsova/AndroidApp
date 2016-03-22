package com.example.myprojectv_002.ResourceItem;

/**
 * Created by Пользователь on 30.11.2015.
 */
public class Setting_item {
    private String titleSetting;
    private int resIcon;
    private String subtitleSetting;

    public Setting_item(String title, int resIcon, String dialog) {
        super();
        this.titleSetting=title;
        this.subtitleSetting=dialog;
        this.resIcon=resIcon;
    }

    public String getTitleSetting(){
        return titleSetting;
    }

    public void  setTitleSetting(String title) {
        this.titleSetting = title;
    }

    public int getResIcon(){
        return  resIcon;
    }

    public void setResIcon(int resIcon){
        this.resIcon=resIcon;
    }

    public String getSubtitleSetting()
    {
        return  subtitleSetting;
    }
    public void setSubtitleSetting(String dial)
    {
        this.subtitleSetting=dial;
    }

}

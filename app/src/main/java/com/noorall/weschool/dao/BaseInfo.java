package com.noorall.weschool.dao;

import org.litepal.crud.DataSupport;

public class BaseInfo extends DataSupport {
    private String userID;
    private String userName;
    private String iconUrl;
    private String schoolID;
    private String password;
    private int startWeek;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getStartWeek() {
        return this.startWeek;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
}

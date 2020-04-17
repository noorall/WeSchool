package com.noorall.weschool.bean;

import java.util.List;

public class CourseBean {
    private int code;
    private String msg;
    private List<CourseData> body;
    public void setCode(int code){
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setBody(List<CourseData> body) {
        this.body = (body!=null)?body:null;
    }
    public List<CourseData> getBody(){
        return this.body;
    }
}

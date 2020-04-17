package com.noorall.weschool.dao;

import org.litepal.crud.DataSupport;

/**
 * @author :Noorall
 * @description:
 * @date :2020/4/8 20:07
 */
public class TimeInfo extends DataSupport {
    private int courseNum;
    private String startTime;
    private String endTime;
    public String getStartTime() {
        return startTime;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}

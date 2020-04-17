/**
 * Copyright 2019 bejson.com
 */
package com.noorall.weschool.bean;

import com.noorall.weschool.dao.CourseInfo;

public class CourseData {

    private int id;
    private String courseId;
    private String courseName;
    private double score;
    private String place;
    private String teacher;
    private int weekday;
    private int startLesson;
    private int endLesson;
    private String classRoom;
    private int startWeek;
    private int endWeek;

    public CourseData() {
    }

    public CourseData(CourseInfo courseInfo) {
        this.courseId = courseInfo.getCourseId();
        this.courseName = courseInfo.getCourseName();
        this.classRoom = courseInfo.getClassRoom();
        this.endLesson = courseInfo.getEndLesson();
        this.endWeek = courseInfo.getEndWeek();
        this.id = courseInfo.getId();
        this.place = courseInfo.getPlace();
        this.score = courseInfo.getScore();
        this.startLesson = courseInfo.getStartLesson();
        this.startWeek = courseInfo.getStartWeek();
        this.teacher = courseInfo.getTeacher();
        this.weekday = courseInfo.getWeekday();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setStartLesson(int startLesson) {
        this.startLesson = startLesson;
    }

    public int getStartLesson() {
        return startLesson;
    }

    public void setEndLesson(int endLesson) {
        this.endLesson = endLesson;
    }

    public int getEndLesson() {
        return endLesson;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

}
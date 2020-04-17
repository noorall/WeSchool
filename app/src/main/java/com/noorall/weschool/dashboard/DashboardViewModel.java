package com.noorall.weschool.dashboard;

import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("打卡记录");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public List<CourseData> getCourseByWeek(int week){
        List<CourseData> courseDataList = new ArrayList<CourseData>();
        List<CourseInfo> courseInfos = DataSupport.where("startWeek <= ? and endWeek >= ?",String.valueOf(week),String.valueOf(week)).find(CourseInfo.class);
        for (CourseInfo courseInfo : courseInfos) {
            if (courseInfo.getStartWeek() <= week && courseInfo.getEndWeek() >= week) {
                CourseData courseData = new CourseData(courseInfo);
                courseDataList.add(courseData);
            }
        }
        return courseDataList;
    }
}
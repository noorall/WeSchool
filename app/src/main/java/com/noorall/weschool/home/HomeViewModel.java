package com.noorall.weschool.home;

import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.ExerciseInfo;
import com.noorall.weschool.utils.BaseData;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mDate;
    private MutableLiveData<String> mWeek;
    private List<Record> mExerciseList;
    private List<CourseData> mCourseList;
    private BaseData baseData;

    public HomeViewModel() {
        baseData = new BaseData();
        mDate = new MutableLiveData<>();
        mWeek = new MutableLiveData<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
        Date date = new Date(System.currentTimeMillis());
        mDate.setValue(dateFormat.format(date));
        mWeek.setValue(weekFormat.format(date));
        //初始化课程列表
        mCourseList = new ArrayList<CourseData>();
        this.updateCourseList();
        //初始化跑操记录
        mExerciseList = new ArrayList<Record>();
        this.updateExerciseRecord();
    }

    private void updateCourseList() {
        Calendar cal = Calendar.getInstance();
        int currentWeekday = cal.get(Calendar.DAY_OF_WEEK);
        if (currentWeekday == 1) {
            currentWeekday = 7;
        } else {
            currentWeekday = currentWeekday - 1;
        }
        this.mCourseList.clear();
        List<CourseInfo> courseInfos = DataSupport.where("weekday = ? and startWeek <= ? and endWeek >= ?", String.valueOf(currentWeekday), String.valueOf(baseData.getCurrentWeek()), String.valueOf(baseData.getCurrentWeek())).order("startLesson").find(CourseInfo.class);
        for (CourseInfo courseInfo : courseInfos) {
            CourseData courseData = new CourseData(courseInfo);
            this.mCourseList.add(courseData);
        }
    }

    private void updateExerciseRecord() {
        mExerciseList.clear();
        List<ExerciseInfo> exerciseInfos = DataSupport.where("QSSJ>?", this.mDate.getValue()).find(ExerciseInfo.class);
        for (int i = 0; i < exerciseInfos.size(); i++) {
            ExerciseInfo exerciseInfo = exerciseInfos.get(i);
            Record record = new Record(exerciseInfo.getQSSJ(), exerciseInfo.getLOCATION(), exerciseInfo.getMINUTES(), exerciseInfo.getKM());
            mExerciseList.add(record);
        }
    }

    public LiveData<String> getDate() {
        return mDate;
    }

    public LiveData<String> getWeek() {
        return mWeek;
    }

    public List<CourseData> getCourseList() {
        return this.mCourseList;
    }

    public List<Record> getExerciseRecord() {
        return this.mExerciseList;
    }
}
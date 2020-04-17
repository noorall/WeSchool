package com.noorall.weschool.utils;

import android.provider.ContactsContract;

import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.TimeInfo;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author :Noorall
 * @description:
 * @date :2020/3/30 11:11
 */
public class BaseData {
    private BaseInfo baseInfo;
    private int weekOfYear;
    private int currentWeek;
    private int currentWeekday;

    public BaseData() {
        baseInfo = DataSupport.findFirst(BaseInfo.class);
        Calendar cal = Calendar.getInstance();
        weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        currentWeek = weekOfYear - baseInfo.getStartWeek();
        currentWeekday = cal.get(Calendar.DAY_OF_WEEK);
        if (currentWeekday == 1) {
            currentWeekday = 7;
        } else {
            currentWeekday = currentWeekday - 1;
        }
    }

    public void updateStartWeek() {
        baseInfo.setStartWeek(8);
        baseInfo.save();
    }

    public void updateTimeInfo() {
        String[][] timeTable = {
                {"08:30", "09:15"},
                {"09:25", "10:10"},
                {"10:30", "11:15"},
                {"11:25", "12:10"},
                {"12:20", "13:05"},
                {"13:05", "13:50"},
                {"14:00", "14:45"},
                {"14:55", "15:40"},
                {"16:00", "16:45"},
                {"16:55", "17:40"},
                {"19:00", "19:45"},
                {"19:55", "20:40"}};
        for (int i = 0; i < 12; i++) {
            TimeInfo timeInfo = new TimeInfo();
            timeInfo.setCourseNum(i + 1);
            timeInfo.setStartTime(timeTable[i][0]);
            timeInfo.setEndTime(timeTable[i][1]);
            timeInfo.save();
        }
    }

    public int getWeekOfYear() {
        return this.weekOfYear;
    }

    public int getCurrentWeek() {
        return this.currentWeek;
    }

    public int getCurrentLesson() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());
        List<TimeInfo> timeInfoList = DataSupport.findAll(TimeInfo.class);
        if (timeInfoList.size() == 0) {
            updateTimeInfo();
            timeInfoList = DataSupport.findAll(TimeInfo.class);
        }
        for (int i = 0; i < timeInfoList.size(); i++) {
            if (timeInfoList.get(i).getStartTime().compareTo(currentTime) < 0
                    && timeInfoList.get(i).getEndTime().compareTo(currentTime) >= 0) {
                return i + 1;
            }
        }
        return 13;
    }

    public int getCurrentWeekday() {
        return this.currentWeekday;
    }

    public String getCurrentWeekday(int i) {
        String[] mWeekTitle = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        return mWeekTitle[this.currentWeekday - 1];
    }
}

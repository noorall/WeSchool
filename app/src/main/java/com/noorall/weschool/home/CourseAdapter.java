package com.noorall.weschool.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noorall.weschool.R;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.TimeInfo;
import com.noorall.weschool.utils.BaseData;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :Noorall
 * @description:
 * @date :2020/3/24 19:31
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<CourseData> mCourseList;
    private List<TimeInfo> timeInfo;

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_course, parent, false);
        CourseAdapter.ViewHolder holder = new CourseAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
        CourseData courseData = mCourseList.get(position);
        holder.courseName.setText(courseData.getCourseName());
        holder.courseSection.setText(courseData.getStartLesson() + "-" + courseData.getEndLesson());
        holder.courseTime.setText(timeInfo.get(courseData.getStartLesson() - 1).getStartTime() + "-" + timeInfo.get(courseData.getEndLesson() - 1).getEndTime());
        holder.classRoom.setText(courseData.getClassRoom());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void setmRecordList(List<CourseData> courseDataList) {
        this.mCourseList = courseDataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseTime;
        TextView courseName;
        TextView classRoom;
        TextView courseSection;

        public ViewHolder(View view) {
            super(view);
            courseTime = view.findViewById(R.id.tv_courseTime);
            courseName = view.findViewById(R.id.tv_courseName);
            courseSection = view.findViewById(R.id.tv_courseSection);
            classRoom = view.findViewById(R.id.tv_classRoom);
        }
    }

    public CourseAdapter(List<CourseData> courseDataList) {
        this.mCourseList = courseDataList;
        this.timeInfo = DataSupport.findAll(TimeInfo.class);
        if (timeInfo.size() == 0) {
            BaseData baseData = new BaseData();
            baseData.updateTimeInfo();
            this.timeInfo = DataSupport.findAll(TimeInfo.class);
        }
    }
}

package com.noorall.weschool.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.noorall.weschool.R;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.utils.BaseData;
import com.noorall.weschool.weight.TimeTableView;

import java.util.List;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DashboardViewModel dashboardViewModel;
    private View root;
    private TimeTableView mTimeTableView;
    private List<CourseData> mListCourse;
    private Spinner mSpinner;
    private BaseData baseData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //初始化数据
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        root = inflater.inflate(R.layout.profiles_dashboard, container, false);
        baseData=new BaseData();
        //视图操作
        mListCourse = dashboardViewModel.getCourseByWeek(baseData.getCurrentWeek());
        mTimeTableView = root.findViewById(R.id.main_timetable_ly);
        //选择框操作
        mSpinner = root.findViewById(R.id.spinner_weeks);
        mSpinner.setSelection(baseData.getCurrentWeek()-1);
        mSpinner.setOnItemSelectedListener(this);
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mListCourse=dashboardViewModel.getCourseByWeek(i+1);
        mTimeTableView.removeAllViews();
        mTimeTableView.setTimeTable(mListCourse);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
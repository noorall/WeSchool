package com.noorall.weschool.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noorall.weschool.R;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.ExerciseInfo;
import com.noorall.weschool.exercise.ExerciseActivity;
import com.noorall.weschool.exercise.ExerciseModel;
import com.noorall.weschool.profiles.AboutActivity;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private List<Record> recordList = new ArrayList<>();
    private List<CourseData> courseList = new ArrayList<>();
    private RecyclerView rv_exercise;
    private RecordAdapter exerciseAdapter;
    private RecyclerView rv_course;
    private CourseAdapter courseAdapter;
    private TextView mExerciseView;

    @SuppressLint("ResourceAsColor")

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //更新时间和星期
        final TextView mtv_date = root.findViewById(R.id.tv_date);
        final TextView mtv_week = root.findViewById(R.id.tv_week);
        rv_exercise = root.findViewById(R.id.recycler_todayExercise);
        rv_course = root.findViewById(R.id.recycler_todayCourse);
        homeViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mtv_date.setText(s);
            }
        });
        homeViewModel.getWeek().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mtv_week.setText(s);
            }
        });
        //
        mExerciseView=root.findViewById(R.id.tv_todayExercise_all);
        mExerciseView.setOnClickListener(this);
        //获取数据
        getCourseData();
        getExerciseData();
        //recycleView 初始化
        LinearLayoutManager exerciseLayoutManager = new LinearLayoutManager(this.getActivity());
        LinearLayoutManager courseLayoutManager = new LinearLayoutManager(this.getActivity());
        //跑操数据
        rv_exercise.setLayoutManager(exerciseLayoutManager);
        exerciseAdapter = new RecordAdapter(recordList);
        rv_exercise.setAdapter(exerciseAdapter);
        //课程数据
        rv_course.setLayoutManager(courseLayoutManager);
        courseAdapter = new CourseAdapter(courseList);
        rv_course.setAdapter(courseAdapter);
        return root;
    }

    private void getExerciseData() {
        this.recordList = homeViewModel.getExerciseRecord();
    }

    private void getCourseData() {
        this.courseList = homeViewModel.getCourseList();
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_todayExercise_all:
                intent = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
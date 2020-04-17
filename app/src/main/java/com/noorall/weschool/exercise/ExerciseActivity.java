package com.noorall.weschool.exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.widget.TextView;

import com.noorall.weschool.R;
import com.noorall.weschool.home.Record;
import com.noorall.weschool.home.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private List<Record> recordList;
    private ExerciseModel exerciseModel;
    private RecyclerView mRecycler;
    private RecordAdapter exerciseAdapter;
    private TextView mExerciseCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        exerciseModel = new ExerciseModel();
        recordList = exerciseModel.getExerciseList();
        //跑操数据
        mRecycler = findViewById(R.id.recycler_exercise_item);
        LinearLayoutManager exerciseLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(exerciseLayoutManager);
        exerciseAdapter = new RecordAdapter(recordList);
        mRecycler.setAdapter(exerciseAdapter);
        //设置计数文本
        mExerciseCount=findViewById(R.id.tv_exercise_count);
        mExerciseCount.setText("已完成打卡"+exerciseModel.getExerciseNum()+"次");
    }
}

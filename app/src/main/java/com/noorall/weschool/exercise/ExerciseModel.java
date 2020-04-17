package com.noorall.weschool.exercise;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noorall.weschool.bean.ExerciseDetailBean;
import com.noorall.weschool.bean.ExerciseDetailData;
import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.dao.ExerciseInfo;
import com.noorall.weschool.home.Record;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExerciseModel {
    private String url;
    private boolean isOk = false;
    private List<Record> mExerciseList;

    public ExerciseModel() {
        mExerciseList = new ArrayList<>();
        updateExerciseRecord();
    }

    public int getExerciseNum() {
        return DataSupport.count(ExerciseInfo.class);
    }

    public void getInfo() {
        //Need to be changed
        BaseInfo baseInfo = DataSupport.findFirst(BaseInfo.class);
        url = "http://222.24.192.216:8085/PublicRequest?RequestName=GetExerciseDetail&RequestData={'userid':'" + baseInfo.getUserID() + "','TERM':'2019下学期'}";
        //第四步:同步get请求
        try {
            //第一步获取okHttpClient对象
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            //第二步构建Request对象
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            //第三步构建Call对象
            Call call = client.newCall(request);
            Response response = call.execute();//必须子线程执行
            String result = response.body().string();
            parseJSONWithGSON(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData) {
        try {
            Gson gson = new Gson();
            ExerciseDetailBean exerciseDetailBean = gson.fromJson(jsonData, new TypeToken<ExerciseDetailBean>() {
            }.getType());
            boolean isError = exerciseDetailBean.getIsError();
            if (isError == false) {
                //need to change
                DataSupport.deleteAll(ExerciseInfo.class);
                List<ExerciseDetailData> exerciseDetailData = exerciseDetailBean.getResult();
                for (int i = 0; i < exerciseDetailData.size(); i++) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    ExerciseDetailData exerciseData = exerciseDetailData.get(i);
                    ExerciseInfo exerciseInfo = new ExerciseInfo();
                    exerciseInfo.setKM(exerciseData.getKM());
                    exerciseInfo.setLOCATION(exerciseData.getLOCATION());
                    exerciseInfo.setMINUTES(exerciseData.getMINUTES());
                    exerciseInfo.setNUM(exerciseData.getNUM());
                    exerciseInfo.setQSSJ(format.parse(exerciseData.getQSSJ()));
                    exerciseInfo.save();
                    this.isOk = true;
                }
            } else {
                this.isOk = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOk() {
        return this.isOk;
    }

    public List<Record> getExerciseList() {
        return this.mExerciseList;
    }

    private void updateExerciseRecord() {
        mExerciseList.clear();
        List<ExerciseInfo> exerciseInfos = DataSupport.findAll(ExerciseInfo.class);
        for (int i = 0; i < exerciseInfos.size(); i++) {
            ExerciseInfo exerciseInfo = exerciseInfos.get(i);
            Record record = new Record(exerciseInfo.getQSSJ(), exerciseInfo.getLOCATION(), exerciseInfo.getMINUTES(), exerciseInfo.getKM());
            mExerciseList.add(record);
        }
    }
}

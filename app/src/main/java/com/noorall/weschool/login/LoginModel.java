package com.noorall.weschool.login;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.noorall.weschool.bean.LoginBean;
import com.noorall.weschool.bean.LoginData;
import com.noorall.weschool.course.CourseModel;
import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.exercise.ExerciseModel;
import com.noorall.weschool.utils.BaseData;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginModel {
    private String url;
    private int code=0;
    //code 0 正在登录 1 密码错误 2 登陆成功（开始获取内容） 3 完成
    public void getLogin(String number, String password) {
        //Need to be changed
        try {
            password = URLEncoder.encode(URLEncoder.encode(password,"utf-8"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = "http://222.24.192.216:8085/PublicRequest?RequestName=UserNoLogin&RequestData={'PassWord':'" + password + "','UserNO':'" + number + "'}";
        //第四步:同步get请求
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    Log.i("LoginActivity",result);
                    parseJSONWithGSON(result);
                } catch (IOException e) {
                    code=1;
                }
            }
        }).start();
    }
    private void parseJSONWithGSON(String jsonData){
        try {
            Gson gson = new Gson();
            LoginBean loginBean=gson.fromJson(jsonData,new TypeToken<LoginBean>(){}.getType());
            boolean isError=loginBean.getIsError();
            if(isError==false) {
                this.code = 2;
                LoginData loginData = loginBean.getResult();
                BaseInfo baseInfo = DataSupport.findFirst(BaseInfo.class);
                baseInfo.setIconUrl(loginData.getIconUrl());
                baseInfo.setSchoolID(loginData.getUserMobilePhone());
                baseInfo.setUserID(String.valueOf(loginData.getUserID()));
                baseInfo.setUserName(loginData.getUserName());
                baseInfo.save();
                final ExerciseModel exerciseModel = new ExerciseModel();
                exerciseModel.getInfo();
                this.code = 3;
            }
            else
            {
                this.code=1;
            }
        } catch (JsonSyntaxException e)
        {
            code=1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getCode()
    {
        return this.code;
    }
}
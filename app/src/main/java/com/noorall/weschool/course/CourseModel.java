package com.noorall.weschool.course;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noorall.weschool.bean.CourseBean;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.TimeInfo;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.sql.Time;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseModel {
    private String cookie;
    private int code=0;

    OkHttpClient client = new OkHttpClient();
    public int getCode(){
        return this.code;
    }
    public void login(String username, String password) {
        Request request = new Request.Builder()
                .url("http://us.nwpu.edu.cn/eams/login.action")
                .get()
                .build();
        final RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("encodePassword", "")
                .add("session_locale", "zh_CN").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("MainActivity", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Headers headers = response.headers();
                String result = response.body().string();
                List<String> cookies = headers.values("Set-Cookie");
                cookie = cookies.get(0);//获取cookie
                //登录操作
                Request request2 = new Request.Builder()
                        .url("http://us.nwpu.edu.cn/eams/login.action")
                        .header("Cookie", cookie)
                        .post(requestBody)
                        .build();
                Call call2 = client.newCall(request2);
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        if (data.indexOf("请输入密码") != -1) {
                            code=1;
                        } else {
                            getData(cookie);
                            code=2;
                        }
                    }
                });
            }
        });
    }

    public void getData(final String cookie) {
        RequestBody requestBody = new FormBody.Builder().build();
        final Request request = new Request.Builder()
                .url("http://us.nwpu.edu.cn/eams/courseTableForStd.action")
                .header("Cookie", cookie)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                //获取课表数据
                String ids = result.substring(result.indexOf("ids") + 6, result.indexOf(")", result.indexOf("ids")) - 1);
                String semesterId = result.substring(result.indexOf("value:") + 7, result.indexOf("}", result.indexOf("value:")) - 1);
                RequestBody requestBody = new FormBody.Builder()
                        .add("ignoreHead", "1")
                        .add("setting.kind", "std")
                        .add("startWeek", "1")
                        .add("project.id", "1")
                        .add("semester.id", semesterId)
                        .add("ids", ids)
                        .build();
                Request request = new Request.Builder()
                        .url("http://us.nwpu.edu.cn/eams/courseTableForStd!courseTable.action")
                        .header("Cookie", cookie)
                        .post(requestBody)
                        .build();
                Call call2 = client.newCall(request);
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        getCourse(result);
                        Log.d("MainActivity", result);
                        System.out.println(result);
                    }
                });
            }
        });
    }

    public void getCourse(String data) {
        RequestBody requestBody = new FormBody.Builder()
                .add("data", data)
                .build();
        final Request request = new Request.Builder()
                .url("https://api.le520.cn/public/index.php/login")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("MainActivity", result);
                System.out.println(result);
                Gson gson = new Gson();
                CourseBean courseBean = gson.fromJson(result, new TypeToken<CourseBean>() {
                }.getType());
                DataSupport.deleteAll(CourseInfo.class);
                List<CourseData> courseList = courseBean.getBody();
                for (CourseData courseData : courseList) {
                    CourseInfo courseInfo = new CourseInfo();
                    courseInfo.setCourseId(courseData.getCourseId());
                    courseInfo.setCourseName(courseData.getCourseName());
                    courseInfo.setClassRoom(courseData.getClassRoom());
                    courseInfo.setEndLesson(courseData.getEndLesson());
                    courseInfo.setId(courseData.getId());
                    courseInfo.setEndWeek(courseData.getEndWeek());
                    courseInfo.setPlace(courseData.getPlace());
                    courseInfo.setScore(courseData.getScore());
                    courseInfo.setStartLesson(courseData.getStartLesson());
                    courseInfo.setStartWeek(courseData.getStartWeek());
                    courseInfo.setTeacher(courseData.getTeacher());
                    courseInfo.setWeekday(courseData.getWeekday());
                    courseInfo.save();
                }
                code=3;
            }
        });
    }

}

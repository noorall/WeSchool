package com.noorall.weschool.login;

import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noorall.weschool.MainActivity;
import com.noorall.weschool.R;
import com.noorall.weschool.course.CourseModel;
import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.utils.BaseData;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    loadingButton.revert();
                    Toast toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
                    toast.setText("账号或密码错误！");
                    toast.show();
                    break;
                default:
                    break;
            }
        }
    };
    private EditText edtTxt_number;
    private EditText edtTxt_password;
    private TextView text_tip;
    private LoadingButton loadingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtTxt_number = findViewById(R.id.edtTxt_number);
        edtTxt_password = findViewById(R.id.edtTxt_password);
        text_tip = findViewById(R.id.text_tip);
        CircularProgressButton button = findViewById(R.id.btn_login);
        loadingButton = new LoadingButton(button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //隐藏软件盘 需要添加判断输入框与按钮的可用性
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                edtTxt_password.clearFocus();
                edtTxt_number.clearFocus();
                text_tip.setVisibility(View.VISIBLE);
                loadingButton.start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String number = edtTxt_number.getText().toString();
                        String password = edtTxt_password.getText().toString();
                        CourseModel courseModel = new CourseModel();
                        courseModel.login(number, password);
                        while (true) {
                            if (courseModel.getCode() == 1) {
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                                break;
                            } else if (courseModel.getCode() == 2) {
                                text_tip.setText("正在获取数据");
                            } else if (courseModel.getCode() == 3) {
                                //设置基础数据
                                BaseInfo baseInfo = new BaseInfo();
                                baseInfo.setStartWeek(8);
                                baseInfo.setSchoolID(number);
                                baseInfo.setPassword(password);
                                baseInfo.save();
                                BaseData baseData = new BaseData();
                                baseData.updateTimeInfo();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                                break;
                            }
                        }
                        text_tip.setVisibility(View.INVISIBLE);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}

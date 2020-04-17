package com.noorall.weschool.profiles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.noorall.weschool.MainActivity;
import com.noorall.weschool.R;
import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.ExerciseInfo;
import com.noorall.weschool.login.LoginActivity;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ProfilesFragment extends Fragment implements View.OnClickListener {

    private ProfilesViewModel profilesViewModel;
    private LinearLayout mPersonAbout;
    private LinearLayout mPersonLogout;
    private LinearLayout mPersonFeedBack;
    private List<Setting> settingList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profilesViewModel =
                ViewModelProviders.of(this).get(ProfilesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profiles, container, false);
        final TextView textView = root.findViewById(R.id.text_sign);
        final TextView tv_username = root.findViewById(R.id.tv_username);
        final BaseInfo baseInfo = DataSupport.findFirst(BaseInfo.class);
        profilesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                tv_username.setText(baseInfo.getUserName());
            }
        });
        mPersonAbout = root.findViewById(R.id.ll_personalAbout);
        mPersonAbout.setOnClickListener(this);
        mPersonLogout = root.findViewById(R.id.ll_personalLogout);
        mPersonLogout.setOnClickListener(this);
        mPersonFeedBack=root.findViewById(R.id.ll_personalFeedBack);
        mPersonFeedBack.setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_personalAbout:
                intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_personalLogout:
                logOut();
                break;
            case R.id.ll_personalFeedBack:
                feedBack();
                break;
        }
    }

    public void logOut() {
        new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("退出会清除您的所有数据，确定退出？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // finish();
                        DataSupport.deleteAll(BaseInfo.class);
                        DataSupport.deleteAll(CourseInfo.class);
                        DataSupport.deleteAll(ExerciseInfo.class);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .show();
    }
    public void feedBack(){
        // 获取view
        View inputView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_feedback, null);
        AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请输入反馈内容")
                .setView(inputView)
                .setNegativeButton("取消",null)
                .setPositiveButton("提交",null)
                .show();
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(alertDialog);
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(18);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
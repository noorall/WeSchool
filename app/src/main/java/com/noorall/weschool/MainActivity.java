package com.noorall.weschool;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.noorall.weschool.dao.BaseInfo;
import com.noorall.weschool.exercise.ExerciseActivity;
import com.noorall.weschool.login.LoginActivity;
import com.noorall.weschool.login.LoginModel;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
        BaseInfo baseInfo = DataSupport.findFirst(BaseInfo.class);
        if(baseInfo==null)
        {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }else if(baseInfo.getUserID()==null){
            LoginModel login=new LoginModel();
            login.getLogin(baseInfo.getSchoolID(),baseInfo.getPassword());
        }
//        //Debug专用跳转
//        Intent intent=new Intent(MainActivity.this, ExerciseActivity.class);
//        startActivity(intent);
//        MainActivity.this.finish();
        //Debug结束
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profiles)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }
}

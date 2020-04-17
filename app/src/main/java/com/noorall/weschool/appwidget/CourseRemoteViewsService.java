package com.noorall.weschool.appwidget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * @author :Noorall
 * @description:
 * @date :2020/4/16 14:22
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CourseRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CourseRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

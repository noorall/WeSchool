package com.noorall.weschool.appwidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.noorall.weschool.R;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.TimeInfo;
import com.noorall.weschool.utils.BaseData;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author :Noorall
 * @description:
 * @date :2020/4/16 14:16
 */
public class CourseRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    public static List<CourseInfo> mCourseList;
    private BaseData baseData;
    private List<TimeInfo> timeInfo;

    /*
     * 构造函数
     */
    public CourseRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        baseData = new BaseData();
        mCourseList = new ArrayList<CourseInfo>();
        timeInfo = DataSupport.findAll(TimeInfo.class);
    }

    /*
     * MyRemoteViewsFactory调用时执行，这个方法执行时间超过20秒回报错。
     * 如果耗时长的任务应该在onDataSetChanged或者getViewAt中处理
     */
    @Override
    public void onCreate() {
        // 需要显示的数据
        this.mCourseList.clear();
        Calendar cal = Calendar.getInstance();
        mCourseList = DataSupport.where("weekday = ? and startWeek <= ? and endWeek >= ? and endLesson >= ?", String.valueOf(baseData.getCurrentWeekday()), String.valueOf(baseData.getCurrentWeek()), String.valueOf(baseData.getCurrentWeek()), String.valueOf(baseData.getCurrentLesson())).order("endLesson").find(CourseInfo.class);
         if (mCourseList.size() == 1) {
            mCourseList.clear();
            mCourseList = DataSupport.where("weekday = ? and startWeek <= ? and endWeek >= ?", String.valueOf(baseData.getCurrentWeekday()), String.valueOf(baseData.getCurrentWeek()), String.valueOf(baseData.getCurrentWeek())).order("endLesson").find(CourseInfo.class);
            for (int i = 0; i < mCourseList.size() && mCourseList.size() > 2 && mCourseList.size() != 1; i++) {
                if (mCourseList.get(i).getEndLesson() < baseData.getCurrentLesson()) {
                    mCourseList.remove(i);
                    i = 0;
                }
            }
        }
    }

    /*
     * 当调用notifyAppWidgetViewDataChanged方法时，触发这个方法
     * 例如：MyRemoteViewsFactory.notifyAppWidgetViewDataChanged();
     */
    @Override
    public void onDataSetChanged() {
        onCreate();
    }

    /*
     * 这个方法不用多说了把，这里写清理资源，释放内存的操作
     */
    @Override
    public void onDestroy() {
        mCourseList.clear();
    }

    /*
     * 返回集合数量
     */
    @Override
    public int getCount() {
        return mCourseList.size();
    }

    /*
     * 创建并且填充，在指定索引位置显示的View，这个和BaseAdapter的getView类似
     */
    @Override
    public RemoteViews getViewAt(int position) {
        CourseInfo courseInfo = mCourseList.get(position);
        // 创建在当前索引位置要显示的View
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.item_appwidget_course);
        // 设置要显示的内容
        rv.setTextViewText(R.id.tv_courseName, courseInfo.getCourseName());
        rv.setTextViewText(R.id.tv_courseSection, courseInfo.getStartLesson() + "-" + courseInfo.getEndLesson());
        rv.setTextViewText(R.id.tv_courseTime, timeInfo.get(courseInfo.getStartLesson() - 1).getStartTime() + "-" + timeInfo.get(courseInfo.getEndLesson() - 1).getEndTime());
        rv.setTextViewText(R.id.tv_classRoom, courseInfo.getClassRoom());
        // 填充Intent，填充在AppWdigetProvider中创建的PendingIntent
        Intent intent = new Intent();
        // 传入点击行的数据
        intent.putExtra("content", courseInfo.getCourseName());
        rv.setOnClickFillInIntent(R.id.tv_courseName, intent);
        return rv;
    }

    /*
     * 显示一个"加载"View。返回null的时候将使用默认的View
     */
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    /*
     * 不同View定义的数量。默认为1（本人一直在使用默认值）
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /*
     * 返回当前索引的。
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * 如果每个项提供的ID是稳定的，即她们不会在运行时改变，就返回true（没用过。。。）
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
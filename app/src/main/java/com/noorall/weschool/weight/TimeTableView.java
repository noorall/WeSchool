package com.noorall.weschool.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.noorall.weschool.MainActivity;
import com.noorall.weschool.R;
import com.noorall.weschool.bean.CourseData;
import com.noorall.weschool.dao.CourseInfo;
import com.noorall.weschool.dao.TimeInfo;
import com.noorall.weschool.utils.BaseData;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 课表显示View
 *
 * @author shallcheek
 */
public class TimeTableView extends LinearLayout {
    /**
     * 配色数组
     */
    public static int color[] = {R.color.course_color_1, R.color.course_color_2,
            R.color.course_color_3, R.color.course_color_4,
            R.color.course_color_5, R.color.course_color_6,
            R.color.course_color_7, R.color.course_color_8,
            R.color.course_color_9, R.color.course_color_10,
            R.color.course_color_11, R.color.course_color_12,
            R.color.course_color_13, R.color.course_color_14,
            R.color.course_color_15, R.color.course_color_16,
            R.color.course_color_17};
    private final static int START = 0;
    //最大节数
    public final static int MAXNUM = 12;
    //显示到星期几
    public final static int WEEKNUM = 7;
    /**
     * 单个View高度
     */
    private final static int TIME_TABLE_HEIGHT = 60;
    /**
     * 线的高度
     */
    private final static int TIME_TABLE_LINE_HEIGHT = 2;
    private final static int LEFT_TITLE_WIDTH = 30;
    private final static int WEEK_TITLE_HEIGHT = 30;
    /**
     * 第一行的星期显示
     */
    private LinearLayout mHorizontalWeekLayout;
    private LinearLayout mVerticalWeekLaout;
    private String[] mWeekTitle = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    public static String[] colorStr = new String[20];
    int colorNum = 0;
    private List<CourseData> mListCourse = new ArrayList<CourseData>();

    private Context mContext;

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 横的分界线
     *
     * @return
     */
    private View getWeekHorizontalLine() {
        View line = new View(getContext());
        line.setBackgroundColor(getResources().getColor(R.color.view_line));
        line.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, TIME_TABLE_LINE_HEIGHT));
        return line;
    }

    /**
     * 竖向分界线
     *
     * @return
     */
    private View getWeekVerticalLine() {
        View line = new View(mContext);
        line.setBackgroundColor(getResources().getColor(R.color.view_line));
        line.setLayoutParams(new ViewGroup.LayoutParams((TIME_TABLE_LINE_HEIGHT), dip2px(WEEK_TITLE_HEIGHT)));
        return line;
    }


    private void initView() {

        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(HORIZONTAL);
        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(HORIZONTAL);

        //表格
        for (int i = 0; i <= WEEKNUM; i++) {
            if (i == 0) {
                layoutLeftNumber();
            } else {
                layoutWeekTitleView(i);
                layoutContentView(i);
            }

            mVerticalWeekLaout.addView(createTableVerticalLine());
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekHorizontalLine());
        addView(mVerticalWeekLaout);
    }

    @NonNull
    private View createTableVerticalLine() {
        View l = new View(getContext());
        l.setLayoutParams(new ViewGroup.LayoutParams(TIME_TABLE_LINE_HEIGHT, dip2px(TIME_TABLE_HEIGHT * MAXNUM) + (MAXNUM - 2) * TIME_TABLE_LINE_HEIGHT));
        l.setBackgroundColor(getResources().getColor(R.color.view_line));
        return l;
    }

    private void layoutContentView(int week) {
        List<CourseData> weekClassList = findWeekClassList(week);
        //添加
        LinearLayout mLayout = createWeekTimeTableView(weekClassList, week);
        mLayout.setOrientation(VERTICAL);
        mLayout.setLayoutParams(new ViewGroup.LayoutParams((getViewWidth() - dip2px(LEFT_TITLE_WIDTH)) / WEEKNUM, LayoutParams.MATCH_PARENT));
        mLayout.setWeightSum(1);
        mVerticalWeekLaout.addView(mLayout);
    }

    /**
     * 遍历出星期1~7的课表
     * 再进行排序
     *
     * @param week 星期
     */
    @NonNull
    private List<CourseData> findWeekClassList(int week) {
        List<CourseData> list = new ArrayList<>();
        for (CourseData courseData : mListCourse) {
            if (courseData.getWeekday() == week) {
                list.add(courseData);
            }
        }

        Collections.sort(list, new Comparator<CourseData>() {
            @Override
            public int compare(CourseData o1, CourseData o2) {
                return o1.getEndLesson() - o2.getStartLesson();
            }
        });

        return list;
    }

    private void layoutWeekTitleView(int weekNumber) {
        TextView weekText = new TextView(getContext());
        weekText.setTextColor(getResources().getColor(R.color.text_color));
        weekText.setWidth(((getViewWidth() - dip2px(LEFT_TITLE_WIDTH))) / WEEKNUM);
        weekText.setHeight(dip2px(WEEK_TITLE_HEIGHT));
        weekText.setGravity(Gravity.CENTER);
        weekText.setTextSize(14);
        weekText.setText(mWeekTitle[weekNumber - 1]);
        weekText.setBackgroundColor(getResources().getColor(R.color.colorTinyGray));
        mHorizontalWeekLayout.addView(weekText);
    }


    private void layoutLeftNumber() {
        //课表出的0,0格子 空白的
        TextView mTime = new TextView(mContext);
        mTime.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(WEEK_TITLE_HEIGHT)));
        mHorizontalWeekLayout.addView(mTime);

        //绘制1~MAXNUM
        LinearLayout numberView = new LinearLayout(mContext);
        numberView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(MAXNUM * TIME_TABLE_HEIGHT) + MAXNUM * 2));
        numberView.setOrientation(VERTICAL);
        List<TimeInfo> timeInfoList = DataSupport.order("courseNum").find(TimeInfo.class);
        if (timeInfoList.size() == 0) {
            BaseData baseData = new BaseData();
            baseData.updateTimeInfo();
            timeInfoList = DataSupport.order("courseNum").find(TimeInfo.class);
        }
        for (int j = 1; j <= MAXNUM; j++) {
            TextView number = createNumberView(j);
            TextView time = creatTimeView(timeInfoList.get(j - 1).getStartTime() + "\n" + timeInfoList.get(j - 1).getEndTime());
            number.setBackgroundColor(getResources().getColor(R.color.colorTinyGray));
            time.setBackgroundColor(getResources().getColor(R.color.colorTinyGray));
            numberView.addView(number);
            numberView.addView(time);
            numberView.addView(getWeekHorizontalLine());
        }
        mVerticalWeekLaout.addView(numberView);
    }

    //绘制左侧课程序号表格
    @NonNull
    private TextView createNumberView(int j) {
        TextView number = new TextView(getContext());
        number.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(TIME_TABLE_HEIGHT / 3)));
        number.setGravity(Gravity.CENTER_HORIZONTAL);
        number.setBottom(0);
        number.setTextColor(getResources().getColor(R.color.colorTitle));
        number.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        number.setTextSize(14);
        number.setText(String.valueOf(j));
        return number;
    }

    private TextView creatTimeView(String timeInfo) {
        TextView time = new TextView(getContext());
        time.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(TIME_TABLE_HEIGHT * 2 / 3)));
        time.setGravity(Gravity.CENTER);
        time.setTextColor(getResources().getColor(R.color.colorText));
        time.setTextSize(10);
        time.setText(timeInfo);
        return time;
    }

    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 绘制空白
     *
     * @param count 数量
     * @param week  星期
     * @param start 用着计算下标
     */
    private View addBlankView(int count, final int week, final int start) {
        LinearLayout blank = new LinearLayout(getContext());
        blank.setOrientation(VERTICAL);
        for (int i = 1; i < count; i++) {
            View classView = new View(getContext());
            classView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(TIME_TABLE_HEIGHT)));
            blank.addView(classView);
            blank.addView(getWeekHorizontalLine());
            final int num = i;
            //这里可以处理空白处点击添加课表
            classView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "星期" + week + "第" + (start + num) + "节", Toast.LENGTH_LONG).show();
                }
            });

        }
        return blank;
    }

    /**
     * 星期一到星期天的课表
     *
     * @param weekList 每天的课程列表
     * @param week     周
     */
    private LinearLayout createWeekTimeTableView(List<CourseData> weekList, int week) {
        LinearLayout weekTableView = new LinearLayout(getContext());
        weekTableView.setOrientation(VERTICAL);
        int size = weekList.size();
        if (weekList.isEmpty()) {
            weekTableView.addView(addBlankView(MAXNUM + 1, week, 0));
        } else {
            for (int i = 0; i < size; i++) {
                CourseData tableModel = weekList.get(i);
                if (i == 0) {
                    //添加的0到开始节数的空格
                    weekTableView.addView(addBlankView(tableModel.getStartLesson(), week, 0));
                    weekTableView.addView(createClassView(tableModel));
                } else if (weekList.get(i).getStartLesson() - weekList.get(i - 1).getEndLesson() > 0) {
                    //填充
                    weekTableView.addView(addBlankView(weekList.get(i).getStartLesson() - weekList.get(i - 1).getEndLesson(), week, weekList.get(i - 1).getEndLesson()));
                    weekTableView.addView(createClassView(weekList.get(i)));
                }
                //绘制剩下的空白
                if (i + 1 == size) {
                    weekTableView.addView(addBlankView(MAXNUM - weekList.get(i).getEndLesson() + 1, week, weekList.get(i).getEndLesson()));
                }
            }
        }
        return weekTableView;
    }

    /**
     * 获取单个课表View 也可以自定义我这个
     *
     * @param model 数据类型
     * @return
     */
    @SuppressWarnings("deprecation")
    private View createClassView(final CourseData model) {
        LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        int num = (model.getEndLesson() - model.getStartLesson());
        mTimeTableView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px((num + 1) * TIME_TABLE_HEIGHT) + (num + 1) * TIME_TABLE_LINE_HEIGHT));

        TextView mTimeTableNameView = new TextView(getContext());
        mTimeTableNameView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px((num + 1) * TIME_TABLE_HEIGHT) + (num) * TIME_TABLE_LINE_HEIGHT));

        mTimeTableNameView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        mTimeTableNameView.setTextSize(12);
        mTimeTableNameView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTimeTableNameView.setGravity(Gravity.CENTER);
        mTimeTableNameView.setText(model.getCourseName() + "\n" + "@" + model.getClassRoom());
        mTimeTableNameView.setPadding(10, 0, 10, 0);
        mTimeTableView.addView(mTimeTableNameView);
        mTimeTableView.addView(getWeekHorizontalLine());
        mTimeTableView.setBackgroundDrawable(getContext().getResources()
                .getDrawable(R.drawable.course_item_bg));
        GradientDrawable gradientDrawable = (GradientDrawable) mTimeTableView.getBackground();
        //gradientDrawable.setColor(color[getColorNum(model.getCourseName())]);
        gradientDrawable.setColor(getContext().getResources().getColor(color[getColorNum(model.getCourseName())]));
        mTimeTableView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_course_details, null);
                TextView mWeek = view.findViewById(R.id.tv_course_week);
                TextView mPlace = view.findViewById(R.id.tv_course_place);
                TextView mTeacher = view.findViewById(R.id.tv_course_teacher);
                TextView mSection = view.findViewById(R.id.tv_course_section);
                TextView mName = view.findViewById(R.id.tv_course_name);
                ImageView mEdit = view.findViewById(R.id.iv_course_edit);
                ImageView mClose = view.findViewById(R.id.iv_course_close);
                mClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mEdit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        courseEdit(model);
                    }
                });
                mName.setText(model.getCourseName());
                mWeek.setText(model.getStartWeek() + "-" + model.getEndWeek() + "周");
                mSection.setText(mWeekTitle[model.getWeekday() - 1] + " " + model.getStartLesson() + "-" + model.getEndLesson() + "节");
                mPlace.setText(model.getClassRoom());
                mTeacher.setText(model.getTeacher());
                dialog.setContentView(view);
                dialog.show();
            }
        });
        return mTimeTableView;
    }

    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    public void setTimeTable(List<CourseData> mlist) {
        this.mListCourse = mlist;
        for (CourseData timeTableModel : mlist) {
            addTimeName(timeTableModel.getCourseName());
        }
        initView();
        invalidate();
    }

    /**
     * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorSt[20]数组
     *
     * @param name
     */
    private void addTimeName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colorNum] = name;
            colorNum++;
        }
    }

    /**
     * 获取数组中的课程名
     *
     * @param name
     * @return
     */
    public static int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }

    public void courseEdit(final CourseData courseData) {
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_course_edit, null);
        final EditText mStartWeek = view.findViewById(R.id.et_course_start_week);
        final EditText mEndWeek = view.findViewById(R.id.et_course_end_week);
        final EditText mStartLesson = view.findViewById(R.id.et_course_start_lesson);
        final EditText mEndLesson = view.findViewById(R.id.et_course_end_lesson);
        final EditText mWeekday = view.findViewById(R.id.et_course_weekday);
        final EditText mTeacher = view.findViewById(R.id.et_course_teacher);
        final EditText mClassRoom = view.findViewById(R.id.et_course_class_room);
        final TextView mCourseName = view.findViewById(R.id.tv_course_name);
        final ImageView mClose = view.findViewById(R.id.iv_course_close);
        Button mSave = view.findViewById(R.id.bt_course_save);
        mStartWeek.setHint(String.valueOf(courseData.getStartWeek()));
        mEndWeek.setHint(String.valueOf(courseData.getEndWeek()));
        mStartLesson.setHint(String.valueOf(courseData.getStartLesson()));
        mEndLesson.setHint(String.valueOf(courseData.getEndLesson()));
        mWeekday.setHint(String.valueOf(courseData.getWeekday()));
        mTeacher.setHint(courseData.getTeacher());
        mClassRoom.setHint(courseData.getClassRoom());
        mCourseName.setText(courseData.getCourseName());
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseInfo courseInfo = DataSupport.where("id = ?", String.valueOf(courseData.getId())).findFirst(CourseInfo.class);
                if (!TextUtils.isEmpty(mStartWeek.getText())) {
                    courseInfo.setStartWeek(Integer.valueOf(mStartWeek.getText().toString()));
                }
                if (!TextUtils.isEmpty(mEndWeek.getText())) {
                    courseInfo.setEndWeek(Integer.valueOf(mEndWeek.getText().toString()));
                }

                if (!TextUtils.isEmpty(mStartLesson.getText())) {
                    courseInfo.setStartLesson(Integer.valueOf(mStartLesson.getText().toString()));
                }
                if (!TextUtils.isEmpty(mEndLesson.getText())) {
                    courseInfo.setEndLesson(Integer.valueOf(mEndLesson.getText().toString()));
                }
                if (!TextUtils.isEmpty(mWeekday.getText())) {
                    courseInfo.setWeekday(Integer.valueOf(mWeekday.getText().toString()));
                }
                if (!TextUtils.isEmpty(mTeacher.getText())) {
                    courseInfo.setTeacher(mTeacher.getText().toString());
                }
                if (!TextUtils.isEmpty(mClassRoom.getText())) {
                    courseInfo.setClassRoom(mClassRoom.getText().toString());
                }
                courseInfo.save();
                showToast("修改成功");
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(this.getContext().getApplicationContext(), null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }
}

package com.noorall.weschool.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noorall.weschool.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Record> mRecordList;
    public RecordAdapter(List<Record> recordList) {
        mRecordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_run_blank, parent, false);
            return new RecyclerView.ViewHolder(emptyView){};
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_run, parent, false);
        CourseViewHolder holder = new CourseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CourseViewHolder){
            CourseViewHolder courseViewHolder=(CourseViewHolder) holder;
            Record record = mRecordList.get(position);
            courseViewHolder.date.setText(record.getDate());
            courseViewHolder.endTime.setText(record.getEndTime());
            courseViewHolder.startTime.setText(record.getStartTime());
            courseViewHolder.km.setText(record.getKm());
            courseViewHolder.location.setText(record.getLocation());
            courseViewHolder.minutes.setText(record.getMinutes());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecordList.size() == 0) {
            return 1;
        }
        return mRecordList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecordList.size() == 0) {
            return 0;
        }
        return 1;
    }

    public void setmRecordList(List<Record> recordList) {
        this.mRecordList = recordList;
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView startTime;
        TextView endTime;
        TextView minutes;
        TextView location;
        TextView km;

        public CourseViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.tv_date);
            startTime = view.findViewById(R.id.tv_startTime);
            endTime = view.findViewById(R.id.tv_endTime);
            minutes = view.findViewById(R.id.tv_minutes);
            location = view.findViewById(R.id.tv_location);
            km = view.findViewById(R.id.tv_km);
        }
    }

}

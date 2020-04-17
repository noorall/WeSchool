package com.noorall.weschool.profiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noorall.weschool.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SetAdapter extends RecyclerView.Adapter <SetAdapter.ViewHolder>{
    private List<Setting> mSetList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_run_blank,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Setting setting=mSetList.get(position);
        holder.setName.setText(setting.getName());
    }

    @Override
    public int getItemCount() {
        return mSetList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView setName;
        public ViewHolder(View view) {
            super(view);
//            setName= view.findViewById(R.id.text_setting);
        }
    }
    public SetAdapter(List<Setting> setList)
    {
        mSetList=setList;
    }

}

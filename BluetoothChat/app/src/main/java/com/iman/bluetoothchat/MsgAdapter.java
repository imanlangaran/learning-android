package com.iman.bluetoothchat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {



    private List<Msg> msgList;
    public MsgAdapter(){
        msgList = new ArrayList<>();
    }

    public void addMessage(String text, boolean isMine){
        Log.i("MsgAdapter", "message added");
        Msg msg = new Msg(text, isMine);
        msgList.add(msg);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        holder.tv.setText(msg.getText());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.tv.getLayoutParams();
        if(msg.isMine()){
            holder.tv.setBackgroundResource(R.drawable.out_bg);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            holder.tv.setBackgroundResource(R.drawable.in_bg);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        holder.tv.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}

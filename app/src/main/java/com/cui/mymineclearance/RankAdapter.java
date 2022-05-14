package com.cui.mymineclearance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 16:40
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.Handler> {
    private ArrayList<RankBean> list;
    public RankAdapter(ArrayList<RankBean> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public Handler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        Handler handler = new Handler(inflate);
        return handler;
    }
    @Override
    public void onBindViewHolder(@NonNull Handler holder, int position) {
        RankBean rankBean = list.get(position);
        holder.id.setText(rankBean.difficult);
        holder.user.setText(rankBean.user);
        holder.ys.setText(rankBean.ys);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class Handler extends RecyclerView.ViewHolder {
        TextView id, user, ys;
        public Handler(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            user = itemView.findViewById(R.id.user);
            ys = itemView.findViewById(R.id.ys);
        }
    }
}

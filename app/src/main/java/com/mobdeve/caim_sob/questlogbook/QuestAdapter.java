package com.mobdeve.caim_sob.questlogbook;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder> {

    private ArrayList<Quest> data;
    public QuestAdapter(ArrayList data){this.data = data; }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.quest, parent, false);
        QuestViewHolder questViewHolder = new QuestViewHolder(view);
        return questViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        String title = data.get(position).getTitle();
        String desc = data.get(position).getDesc();
        QuestType type = data.get(position).getType();
        holder.setDetails(title, desc, type);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void deleteItem(int position) {
        this.data.remove(position);
        notifyItemRemoved(position);
    }
}

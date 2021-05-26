package com.mobdeve.caim_sob.questlogbook;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder> {

    private QuestDBHelper questDBHelper;
    private Context context;
    private ArrayList<Quest> data;
    private Boolean isTemplate;
    public QuestAdapter(ArrayList data, Boolean isTemplate){
        this.data = data;
        this.isTemplate = isTemplate;
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        questDBHelper = new QuestDBHelper(this.context);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.quest, parent, false);
        QuestViewHolder questViewHolder = new QuestViewHolder(view, isTemplate);
        return questViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        String title = data.get(position).getTitle();
        String desc = data.get(position).getDesc();
        QuestType type = data.get(position).getType();
        holder.setDetails(title, desc, type, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void deleteQuest(int position, Boolean isTemplate) {
        int id = data.get(position).getId();
        Boolean success = false;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Notifications.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, i, 0);

        if(isTemplate){
            success = this.questDBHelper.deleteQuestTemplateData(id);
            alarmManager.cancel(pendingIntent);
        } else {
            success = this.questDBHelper.deleteQuestInstanceData(id);
        }
        if (success){
            this.data.remove(position);
            notifyItemRemoved(position);
        }
    }
}

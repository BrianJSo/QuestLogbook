package com.mobdeve.caim_sob.questlogbook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class QuestViewHolder extends RecyclerView.ViewHolder {

    private Quest quest;
    private TextView title;
    private TextView desc;
    private TextView type;

    public QuestViewHolder(@NonNull View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.questInstanceTitle);
        this.desc = itemView.findViewById(R.id.questInstanceDesc);
        this.type = itemView.findViewById(R.id.questInstanceType);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());

                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                View dialogView = inflater.inflate(R.layout.activity_quest, (ViewGroup) itemView, false);
                dialogBuilder.setView(dialogView);

                TextView questTitleTv = (TextView) dialogView.findViewById(R.id.viewQuestTitleTv);
                questTitleTv.setText(quest.getTitle());
                TextView questDescTv = (TextView) dialogView.findViewById(R.id.viewQuestDescTv);
                questDescTv.setText(quest.getDesc());
                if(quest.getDesc().equals("")){
                    questDescTv.setText("TODO");
                }
                TextView questActivationTv = (TextView) dialogView.findViewById(R.id.viewQuestActivationTv);
                questActivationTv.setText(quest.getType().name());
                EditText editText = (EditText) dialogView.findViewById(R.id.viewQuestNotesTv);
                editText.setText(quest.getNotes());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void setDetails(String title, String desc, QuestType type, Quest quest){
        this.quest = quest;
        this.title.setText(title);
        if(desc.equals("")){
            this.desc.setText("TODO");
        } else {
            this.desc.setText(desc);
        }
        this.type.setText(type.name());
    }
}

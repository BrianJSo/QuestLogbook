package com.mobdeve.caim_sob.questlogbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public QuestViewHolder(@NonNull View itemView, Boolean isTemplate) {
        super(itemView);

        this.title = itemView.findViewById(R.id.questInstanceTitle);
        this.desc = itemView.findViewById(R.id.questInstanceDesc);
        this.type = itemView.findViewById(R.id.questInstanceType);

        itemView.setOnClickListener(v -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());

            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            int layout;
            if(isTemplate){
                layout = R.layout.quest_template_edit;
            } else {
                layout = R.layout.activity_quest;
            }

            View dialogView = inflater.inflate(layout, (ViewGroup) itemView, false);
            dialogBuilder.setView(dialogView);

            int titleView;
            int descView;
            if (isTemplate){
                titleView = R.id.editQuestTitleEt;
                descView = R.id.editQuestDescEt;
                EditText questTitleEt = dialogView.findViewById(titleView);
                EditText questDescEt = dialogView.findViewById(descView);
                questTitleEt.setText(quest.getTitle());
                questDescEt.setText(quest.getDesc());
            } else {
                titleView = R.id.viewQuestTitleTv;
                descView = R.id.viewQuestDescTv;
                TextView questTitleTv = dialogView.findViewById(titleView);
                questTitleTv.setText(quest.getTitle());
                TextView questDescTv = dialogView.findViewById(descView);
                questDescTv.setText(quest.getDesc());
                if(quest.getDesc().equals("")){
                    questDescTv.setText("TODO");
                }
            }

            // QUEST TYPE
            QuestType type = quest.getType();
            String activation = " - ";
            int hour = quest.getHour();
            int minute = quest.getMinute();
            switch (type){
                case DAILY:
                    activation += format2Digit(hour)+":"+format2Digit(minute);
                    break;
                case WEEKLY:
                    activation += quest.getDayOfWeek().name()+" "+format2Digit(hour)+":"+format2Digit(minute);
                    break;
                case SCHEDULE:
                    int dayOfMonth = quest.getDayOfMonth();
                    int month = quest.getMonth();
                    int year = quest.getYear();
                    String date = format2Digit(dayOfMonth)+"/"+format2Digit(month)+"/"+year;
                    activation += date+" "+format2Digit(hour)+":"+format2Digit(minute);
                    break;
                case QUICK:
                    activation = "";
            }
            int typeView = R.id.viewQuestTypeTv;
            if (isTemplate){
                typeView = R.id.editQuestTypeTv;
            }
            TextView questTypeTv = dialogView.findViewById(typeView);
            questTypeTv.setText(quest.getType().name()+" QUEST"+activation);

            // QUEST NOTES
            int notesView = R.id.viewQuestNotesTv;
            if (isTemplate) {
                notesView = R.id.editQuestNotesEt;
            }
            EditText notesEt = dialogView.findViewById(notesView);
            notesEt.setText(quest.getNotes());
            int position = notesEt.length();
            Editable etext = notesEt.getText();
            Selection.setSelection(etext, position);

            // ALERT AND UPDATE BTN
            AlertDialog alertDialog = dialogBuilder.create();
            int btnView = R.id.updateNotesBtn;
            if (isTemplate){
                btnView = R.id.editQuestBtn;
            }
            Button updateBtn = dialogView.findViewById(btnView);
            updateBtn.setOnClickListener(v1 -> {
                QuestDBHelper dbHelper = new QuestDBHelper(dialogView.getContext());
                if(!isTemplate){
                    if (dbHelper.updateInstanceNotes(quest.getId(), notesEt.getText().toString())){
                        quest.setNotes(notesEt.getText().toString());
                        alertDialog.dismiss();
                    }
                } else {
                    EditText questTitleEt = dialogView.findViewById(titleView);
                    EditText questDescEt = dialogView.findViewById(descView);
                    String questTitle = questTitleEt.getText().toString();
                    String questDesc = questDescEt.getText().toString();
                    questDescEt.setText(quest.getDesc());
                    if (dbHelper.updateTemplateData(quest.getId(), questTitle, questDesc, notesEt.getText().toString())){
                        quest.setTitle(questTitle);
                        quest.setDesc(questDesc);
                        quest.setNotes(notesEt.getText().toString());
                        setDetails(quest);
                        alertDialog.dismiss();
                    }
                }
            });
            alertDialog.show();
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

    public void setDetails(Quest quest){
        this.quest = quest;
        this.title.setText(quest.getTitle());
        if(desc.equals("")){
            this.desc.setText("TODO");
        } else {
            this.desc.setText(quest.getDesc());
        }
        this.type.setText(quest.getType().name());
    }

    private String format2Digit(int num){
        String str = "0"+num;
        return str.substring(str.length()-2);
    }
}

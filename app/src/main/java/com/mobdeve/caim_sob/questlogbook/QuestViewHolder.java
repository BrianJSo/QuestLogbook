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

    public QuestViewHolder(@NonNull View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.questInstanceTitle);
        this.desc = itemView.findViewById(R.id.questInstanceDesc);
        this.type = itemView.findViewById(R.id.questInstanceType);

        itemView.setOnClickListener(v -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());

            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            View dialogView = inflater.inflate(R.layout.activity_quest, (ViewGroup) itemView, false);
            dialogBuilder.setView(dialogView);

            TextView questTitleTv = dialogView.findViewById(R.id.viewQuestTitleTv);
            questTitleTv.setText(quest.getTitle());
            TextView questDescTv = dialogView.findViewById(R.id.viewQuestDescTv);
            questDescTv.setText(quest.getDesc());
            if(quest.getDesc().equals("")){
                questDescTv.setText("TODO");
            }
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
            TextView questTypeTv = dialogView.findViewById(R.id.viewQuestTypeTv);
            questTypeTv.setText(quest.getType().name()+" QUEST"+activation);
            EditText notesEt = dialogView.findViewById(R.id.viewQuestNotesTv);
            int position = notesEt.length();
            notesEt.setText(quest.getNotes());
//            Editable etext = notesEt.getText();
//            Selection.setSelection(etext, position);

            AlertDialog alertDialog = dialogBuilder.create();
            Button updateNotesBtn = dialogView.findViewById(R.id.updateNotesBtn);
            updateNotesBtn.setOnClickListener(v1 -> {
                Log.d("buboi", "1");
                if (!notesEt.getText().toString().equals(quest.getNotes())){
                    Log.d("buboi", "2");
                    Log.d("buboi", "id = "+quest.getId());
                    QuestDBHelper dbHelper = new QuestDBHelper(dialogView.getContext());

                    if(quest.getType().equals(QuestType.QUICK)){
                        if (dbHelper.updateInstanceNotes(quest.getId(), notesEt.getText().toString())){
                            Log.d("buboi", "3");
                            quest.setNotes(notesEt.getText().toString());
                            alertDialog.dismiss();
                        }
                    } else {
                        if (dbHelper.updateTemplateNotes(quest.getId(), notesEt.getText().toString())){
                            Log.d("buboi", "3");
                            quest.setNotes(notesEt.getText().toString());
                            alertDialog.dismiss();
                        }
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

    private String format2Digit(int num){
        String str = "0"+num;
        return str.substring(str.length()-2);
    }
}

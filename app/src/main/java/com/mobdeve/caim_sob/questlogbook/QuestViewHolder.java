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
//                Intent i = new Intent(itemView.getContext(), QuestActivity.class);
//                itemView.getContext().startActivity(i);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
// ...Irrelevant code for customizing the buttons and title
//                LayoutInflater inflater = this.getLayoutInflater();
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                View dialogView = inflater.inflate(R.layout.activity_quest, (ViewGroup) itemView, false);
                dialogBuilder.setView(dialogView);

                EditText editText = (EditText) dialogView.findViewById(R.id.viewQuestNotesTv);
                editText.setText("test label");
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void setDetails(String title, String desc, QuestType type){
        this.title.setText(title);
        this.desc.setText(title);
        this.type.setText(title);
    }
}

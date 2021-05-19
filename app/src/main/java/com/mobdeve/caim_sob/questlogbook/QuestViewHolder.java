package com.mobdeve.caim_sob.questlogbook;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
                Intent i = new Intent(itemView.getContext(), QuestActivity.class);
                itemView.getContext().startActivity(i);
            }
        });
    }

    public void setDetails(String title, String desc, QuestType type){
        this.title.setText(title);
        this.desc.setText(title);
        this.type.setText(title);
    }
}

package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuestTemplates extends AppCompatActivity {

    private QuestDBHelper questDBHelper;

    private ArrayList templateList;
    private Button toQuestMakerBtn;
    private RecyclerView templateQuestsRv;
    private LinearLayoutManager linearLayoutManager;
    private QuestAdapter questAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_templates);

        questDBHelper = new QuestDBHelper(this);

        this.templateList = new ArrayList<Quest>();
        populateList(templateList);

        this.toQuestMakerBtn = findViewById(R.id.toQuestMakerBtn);
        this.templateQuestsRv = findViewById(R.id.activeTemplatesRV);

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.templateQuestsRv.setLayoutManager(this.linearLayoutManager);
        this.questAdapter = new QuestAdapter(templateList);
        this.templateQuestsRv.setAdapter(this.questAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(this.questAdapter));
        itemTouchHelper.attachToRecyclerView(this.templateQuestsRv);

        this.toQuestMakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuestTemplates.this, QuestMakerForm.class);
                startActivity(i);
            }
        });
    }

    public void reloadList(){
        this.templateList.clear();
        Quest sample;
        Cursor results = this.questDBHelper.getQuestTemplates();
        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(0));
            String title = results.getString(1);
            sample = new Quest(id, title);
            templateList.add(sample);
        }
    }

    public void populateList(ArrayList<Quest> questList){
        Quest sample;
        Cursor results = this.questDBHelper.getQuestTemplates();
        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(0));
            String title = results.getString(1);
            sample = new Quest(id, title);
            questList.add(sample);
        }
    }
}
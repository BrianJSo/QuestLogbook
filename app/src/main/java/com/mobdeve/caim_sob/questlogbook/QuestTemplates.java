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

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuestTemplates extends AppCompatActivity {

    private QuestDBHelper questDBHelper;

    private ArrayList templateList;
    private Button toQuestMakerBtn;
    private RecyclerView templateQuestsRv;
    private LinearLayoutManager linearLayoutManager;
    private QuestAdapter questAdapter;
    private Chip byAlphabetical;
    private Chip byTime;
    private Boolean alphabetical = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_templates);

        questDBHelper = new QuestDBHelper(this);

        this.templateList = new ArrayList<Quest>();
        populateList(templateList);

        this.toQuestMakerBtn = findViewById(R.id.toQuestMakerBtn);
        this.templateQuestsRv = findViewById(R.id.activeTemplatesRV);
        this.byAlphabetical = findViewById(R.id.tempSortAlpha);
        this.byTime = findViewById(R.id.tempSortTime);

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.templateQuestsRv.setLayoutManager(this.linearLayoutManager);
        this.questAdapter = new QuestAdapter(templateList, true);
        this.templateQuestsRv.setAdapter(this.questAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(this.questAdapter, true));
        itemTouchHelper.attachToRecyclerView(this.templateQuestsRv);

        ItemTouchHelper itemTouchHelper2 = new
                ItemTouchHelper(new SimpleCallback(this.questAdapter));
        itemTouchHelper2.attachToRecyclerView(this.templateQuestsRv);

        this.toQuestMakerBtn.setOnClickListener(v -> {
            Intent i = new Intent(QuestTemplates.this, QuestMakerForm.class);
            startActivity(i);
        });

        byAlphabetical.setOnClickListener(v -> {
            alphabetical = true;
            reloadList();
        });

        byTime.setOnClickListener(v -> {
            alphabetical = false;
            reloadList();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    public void reloadList(){
        this.templateList.clear();
        Quest sample;
        Cursor results;
        if (alphabetical){
            results = this.questDBHelper.getQuestTemplatesAlphabetical();
        } else {
            results = this.questDBHelper.getQuestTemplates();
        }

        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(results.getColumnIndex("id")));
            String title = results.getString(results.getColumnIndex("title"));
            String desc = results.getString(results.getColumnIndex("description"));
            String notes = results.getString(results.getColumnIndex("notes"));
            String strType = results.getString(results.getColumnIndex("type"));
            QuestType type = QuestType.valueOf(strType);
            int hour = results.getInt(results.getColumnIndex("hour"));
            int minute = results.getInt(results.getColumnIndex("minute"));
            switch (type){
                case WEEKLY:
                    String strDay = results.getString(results.getColumnIndex("dayOfWeek"));
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(strDay);
                    sample = new Quest(id, title, desc, notes, hour, minute, dayOfWeek);
                    break;
                case SCHEDULE:
                    int dayOfMonth = results.getInt(results.getColumnIndex("dayOfMonth"));
                    int month = results.getInt(results.getColumnIndex("month"));
                    int year = results.getInt(results.getColumnIndex("year"));
                    sample = new Quest(id, title, desc, notes, hour, minute, dayOfMonth, month, year);
                    break;
                default: // DAILY
                    sample = new Quest(id, title, desc, notes, hour, minute); break;
            }
            this.templateList.add(sample);
        }
        this.questAdapter.notifyDataSetChanged();
    }

    public void populateList(ArrayList<Quest> questList){
        Quest sample;
        Cursor results = this.questDBHelper.getQuestTemplates();
        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(results.getColumnIndex("id")));
            String title = results.getString(results.getColumnIndex("title"));
            String desc = results.getString(results.getColumnIndex("description"));
            String notes = results.getString(results.getColumnIndex("notes"));
            String strType = results.getString(results.getColumnIndex("type"));
            QuestType type = QuestType.valueOf(strType);
            int hour = results.getInt(results.getColumnIndex("hour"));
            int minute = results.getInt(results.getColumnIndex("minute"));
            switch (type){
                case WEEKLY:
                    String strDay = results.getString(results.getColumnIndex("dayOfWeek"));
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(strDay);
                    sample = new Quest(id, title, desc, notes, hour, minute, dayOfWeek);
                    break;
                case SCHEDULE:
                    int dayOfMonth = results.getInt(results.getColumnIndex("dayOfMonth"));
                    int month = results.getInt(results.getColumnIndex("month"));
                    int year = results.getInt(results.getColumnIndex("year"));
                    sample = new Quest(id, title, desc, notes, hour, minute, dayOfMonth, month, year);
                    break;
                default: // DAILY
                    sample = new Quest(id, title, desc, notes, hour, minute); break;
            }
            questList.add(sample);
        }
    }


}
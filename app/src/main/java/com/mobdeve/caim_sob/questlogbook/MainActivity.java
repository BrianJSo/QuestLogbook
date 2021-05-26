package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private QuestDBHelper questDB;

    private ArrayList questList;
    private Button toQuestTemplateBtn;
    private FloatingActionButton toQuickCreateBtn;
    private RecyclerView activeQuestsRv;
    private LinearLayoutManager linearLayoutManager;
    private QuestAdapter questAdapter;
    private Chip byAlphabetical;
    private Chip byTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questDB = new QuestDBHelper(this);

        this.questList = new ArrayList<Quest>();
        populateList(questList);

        this.toQuestTemplateBtn = findViewById(R.id.toQuestTemplateBtn);
        this.toQuickCreateBtn = findViewById(R.id.toQuickCreateBtn);
        this.activeQuestsRv = findViewById(R.id.activeQuestsRv);
        this.byAlphabetical = findViewById(R.id.mainSortAlpha);
        this.byTime = findViewById(R.id.mainSortTime);

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.activeQuestsRv.setLayoutManager(this.linearLayoutManager);
        this.questAdapter = new QuestAdapter(questList, false);
        this.activeQuestsRv.setAdapter(this.questAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(this.questAdapter));
        itemTouchHelper.attachToRecyclerView(this.activeQuestsRv);

        ItemTouchHelper itemTouchHelper2 = new
                ItemTouchHelper(new SimpleCallback(this.questAdapter));
        itemTouchHelper2.attachToRecyclerView(this.activeQuestsRv);

        this.toQuestTemplateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuestTemplates.class);
                startActivity(i);
            }
        });

        toQuickCreateBtn.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            final EditText edittext = new EditText(MainActivity.this);
            alert.setMessage("New Quest");
            alert.setTitle("Enter Quest Title");

            alert.setView(edittext);

            alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String questTitle = edittext.getText().toString();
                    int result = MainActivity.this.questDB.insertQuestInstanceData(questTitle, "TODO", "");
                    if (result>-1){
                        reloadList();
                    }
                }
            });

            alert.show();
        });

        byAlphabetical.setOnClickListener(v -> {

        });

        byTime.setOnClickListener(v -> {

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    public void reloadList(){
        this.questList.clear();
        Quest sample;
        Cursor results = this.questDB.getQuestInstances();
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
                case DAILY:
                    sample = new Quest(id, title, desc, notes, hour, minute); break;
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
                default: // QUICK
                    sample = new Quest(id, title, desc, notes);
            }
            this.questList.add(sample);
        }
        this.questAdapter.notifyDataSetChanged();
    }

    public void populateList(ArrayList<Quest> questList){
        Quest sample;
        Cursor results = this.questDB.getQuestInstances();
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
                case DAILY:
                    sample = new Quest(id, title, desc, notes, hour, minute); break;
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
                default: // QUICK
                    sample = new Quest(id, title, desc, notes);
            }
            questList.add(sample);
        }
    }
}
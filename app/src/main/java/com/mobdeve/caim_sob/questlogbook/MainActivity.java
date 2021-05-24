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

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.activeQuestsRv.setLayoutManager(this.linearLayoutManager);
        this.questAdapter = new QuestAdapter(questList);
        this.activeQuestsRv.setAdapter(this.questAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(this.questAdapter));
        itemTouchHelper.attachToRecyclerView(this.activeQuestsRv);

        this.toQuestTemplateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuestTemplates.class);
                startActivity(i);
            }
        });

        toQuickCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                final EditText edittext = new EditText(MainActivity.this);
                alert.setMessage("New Quest");
                alert.setTitle("Enter Quest Title");

                alert.setView(edittext);

                alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String questTitle = edittext.getText().toString();
                        Boolean success = MainActivity.this.questDB.insertQuestInstanceData(questTitle, "TODO", "");
                        Log.d("BUBOI", String.valueOf(success));
                        if (success){
                            reloadList();
                        }
                    }
                });

                alert.show();
            }
        });
    }

    public void reloadList(){
        this.questList.clear();
        Quest sample;
        Cursor results = this.questDB.getQuestInstances();
        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(0));
            String title = results.getString(1);
            sample = new Quest(id, title, "", "");
            questList.add(sample);
        }
        this.questAdapter.notifyDataSetChanged();
    }

    public void populateList(ArrayList<Quest> questList){
        Quest sample;
        Cursor results = this.questDB.getQuestInstances();
        while (results.moveToNext()){
            int id = Integer.parseInt(results.getString(0));
            String title = results.getString(1);
            sample = new Quest(id, title, "", "");
            questList.add(sample);
        }
    }
}
package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList questList;
    private Button toQuestMakerBtn;
    private FloatingActionButton toQuickCreateBtn;
    private RecyclerView activeQuestsRv;
    private LinearLayoutManager linearLayoutManager;
    private QuestAdapter questAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.questList = new ArrayList<Quest>();
        populateList(questList);

        this.toQuestMakerBtn = findViewById(R.id.toQuestMakerBtn);
        this.toQuickCreateBtn = findViewById(R.id.toQuickCreateBtn);
        this.activeQuestsRv = findViewById(R.id.activeQuestsRv);

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.activeQuestsRv.setLayoutManager(this.linearLayoutManager);
        this.questAdapter = new QuestAdapter(questList);
        this.activeQuestsRv.setAdapter(this.questAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(this.questAdapter));
        itemTouchHelper.attachToRecyclerView(this.activeQuestsRv);

        this.toQuestMakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuestMakerForm.class);
                startActivity(i);
            }
        });
    }

    public static void populateList(ArrayList<Quest> questList){
        Quest sample;
        for (int i=0; i<20; i++){
            sample = new Quest("buboi");
            questList.add(sample);
        }
    }
}
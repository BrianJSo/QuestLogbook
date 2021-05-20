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
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    QuestInstanceDBHelper questInstanceDb;

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

        questInstanceDb = new QuestInstanceDBHelper(this);

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
                        Boolean success = MainActivity.this.questInstanceDb.insertQuestInstanceData(questTitle, "TODO", "");
                        Log.d("BUBOI", String.valueOf(success));
                        if (success){
                            reloadList();
                            MainActivity.this.questAdapter.notifyDataSetChanged();
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
        Cursor results = this.questInstanceDb.getData();
        while (results.moveToNext()){
            sample = new Quest(results.getString(0));
            questList.add(sample);
        }
    }

    public void populateList(ArrayList<Quest> questList){
        Quest sample;
        Cursor results = this.questInstanceDb.getData();
        while (results.moveToNext()){
            sample = new Quest(results.getString(0));
            questList.add(sample);
        }
    }
}
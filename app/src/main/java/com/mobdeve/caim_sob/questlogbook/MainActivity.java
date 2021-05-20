package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                        //What ever you want to do with the value
                        //OR
                        String YouEditTextValue = edittext.getText().toString();
                    }
                });

                alert.show();
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
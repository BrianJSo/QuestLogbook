package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Button toQuestMakerBtn;
    private FloatingActionButton toQuickCreateBtn;
    private RecyclerView activeQuestsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toQuestMakerBtn = findViewById(R.id.toQuestMakerBtn);
        this.toQuickCreateBtn = findViewById(R.id.toQuickCreateBtn);
        this.activeQuestsRv = findViewById(R.id.activeQuestsRv);

        this.toQuestMakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuestMakerForm.class);
                startActivity(i);
            }
        });
    }
}
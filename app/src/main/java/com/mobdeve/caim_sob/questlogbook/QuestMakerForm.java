package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class QuestMakerForm extends AppCompatActivity {

    private Switch rpt;
    private ChipGroup questType;
    private Chip daily;
    private Chip weekly;
    private Chip sched;
    private EditText hour;
    private EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_maker_form);

        this.rpt = findViewById(R.id.formRepeatBtn);
        this.questType = findViewById(R.id.formQuestType);
        this.daily = findViewById(R.id.formDailyBtn);
        this.weekly = findViewById(R.id.formWeeklyBtn);
        this.sched = findViewById(R.id.formScheduleBtn);
        this.hour = findViewById(R.id.formTimeET);
        this.date = findViewById(R.id.formDateET);

        rpt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    questType.setVisibility(View.VISIBLE);
                    hour.setVisibility(View.VISIBLE);
                }
                else{
                    questType.setVisibility(View.GONE);
                    hour.setVisibility(View.GONE);
                    date.setVisibility(View.GONE);
                }
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setVisibility(View.GONE);
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setVisibility(View.GONE);
            }
        });

        sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setVisibility(View.VISIBLE);
            }
        });
    }
}
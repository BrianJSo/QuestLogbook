package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class QuestMakerForm extends AppCompatActivity{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch rpt;
    private ChipGroup questType;
    private Chip daily;
    private Chip weekly;
    private Chip sched;
    private EditText formTitleET;
    private EditText formDescET;
    private EditText formNotesET;
    private EditText hour;
    private EditText date;
    private Button post;
    private ScrollView sv;
    boolean isRepeatable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_maker_form);

        this.formTitleET = findViewById(R.id.formTitleET);
        this.formDescET = findViewById(R.id.formDescET);
        this.formNotesET = findViewById(R.id.formNotesET);
        this.formTitleET = findViewById(R.id.formTitleET);
        this.rpt = findViewById(R.id.formRepeatBtn);
        this.questType = findViewById(R.id.formQuestType);
        this.daily = findViewById(R.id.formDailyBtn);
        this.weekly = findViewById(R.id.formWeeklyBtn);
        this.sched = findViewById(R.id.formScheduleBtn);
        this.hour = findViewById(R.id.formTimeET);
        this.date = findViewById(R.id.formDateET);
        this.post = findViewById(R.id.formPostBtn);
        this.sv = findViewById(R.id.formWeeklyDates);

        rpt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                isRepeatable = true;
                questType.setVisibility(View.VISIBLE);
                hour.setVisibility(View.VISIBLE);
            }
            else{
                isRepeatable = false;
                questType.setVisibility(View.GONE);
                hour.setVisibility(View.GONE);
                date.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
                daily.setChecked(false);
                weekly.setChecked(false);
                sched.setChecked(false);
            }
        });

        daily.setOnClickListener(v -> {
            date.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);
        });

        weekly.setOnClickListener(v -> {
            date.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
        });

        sched.setOnClickListener(v -> {
            date.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        });

        post.setOnClickListener(v -> {
            String title = formTitleET.getText().toString();
            String desc = formDescET.getText().toString();
            String notes = formNotesET.getText().toString();

            if (isRepeatable){
                if (daily.isCheckedIconVisible()){
//                if daily

                } else if (weekly.isCheckedIconVisible()){
//                if weekly

                } else if (sched.isCheckedIconVisible()){
//                if schedule

                }
            } else {

            }
            finish();
        });
    }
}
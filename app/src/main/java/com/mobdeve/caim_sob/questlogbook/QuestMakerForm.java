package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;

public class QuestMakerForm extends AppCompatActivity{

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch rpt;
    private ChipGroup questType;
    private Chip daily;
    private Chip weekly;
    private Chip sched;
    private TimePicker timePicker;
    private LinearLayout timeGroupLl;
    private LinearLayout dateGroupLl;
    private TextView timeTv;
    private TextView dateTv;
    private Button setTimeBtn;
    private Button setDateBtn;
    private EditText formTitleET;
    private EditText formDescET;
    private EditText formNotesET;
    private Button post;
    private ScrollView sv;
    boolean isRepeatable = false;

    private Calendar calendar;
    private int dayOfMonth;
    private int month;
    private int year;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_maker_form);

        calendar = Calendar.getInstance();

        this.timeGroupLl = findViewById(R.id.timeGroupLl);
        this.dateGroupLl = findViewById(R.id.dateGroupLl);
        this.timeTv = findViewById(R.id.formTimeTv);
        this.dateTv = findViewById(R.id.formDateTv);
        this.setTimeBtn = findViewById(R.id.setTimeBtn);
        this.setDateBtn = findViewById(R.id.setDateBtn);
        this.formTitleET = findViewById(R.id.formTitleET);
        this.formDescET = findViewById(R.id.formDescET);
        this.formNotesET = findViewById(R.id.formNotesET);
        this.formTitleET = findViewById(R.id.formTitleET);
        this.rpt = findViewById(R.id.formRepeatBtn);
        this.questType = findViewById(R.id.formQuestType);
        this.daily = findViewById(R.id.formDailyBtn);
        this.weekly = findViewById(R.id.formWeeklyBtn);
        this.sched = findViewById(R.id.formScheduleBtn);
        this.post = findViewById(R.id.formPostBtn);
        this.sv = findViewById(R.id.formWeeklyDates);

        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        dateTv.setText(format2Digit(dayOfMonth)+"-"+format2Digit(month+1)+"-"+year);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        timeTv.setText(format2Digit(hour)+":"+format2Digit(minute));

        rpt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                isRepeatable = true;
                questType.setVisibility(View.VISIBLE);
                timeGroupLl.setVisibility(View.VISIBLE);
            }
            else{
                isRepeatable = false;
                questType.setVisibility(View.GONE);
                timeGroupLl.setVisibility(View.GONE);
                dateGroupLl.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
                daily.setChecked(false);
                weekly.setChecked(false);
                sched.setChecked(false);
            }
        });

        daily.setOnClickListener(v -> {
            dateGroupLl.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);
        });

        weekly.setOnClickListener(v -> {
            dateGroupLl.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
        });

        sched.setOnClickListener(v -> {
            dateGroupLl.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        });

        setTimeBtn.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        QuestMakerForm.this.hour = hourOfDay;
                        QuestMakerForm.this.minute = minute;
                        timeTv.setText(format2Digit(hourOfDay)+":"+format2Digit(minute));
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        setDateBtn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        QuestMakerForm.this.year = year;
                        QuestMakerForm.this.month = month;
                        QuestMakerForm.this.dayOfMonth = dayOfMonth;
                        dateTv.setText(format2Digit(dayOfMonth)+"-"+format2Digit(month+1)+"-"+year);
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

        post.setOnClickListener(v -> {
            String title = formTitleET.getText().toString();
            String desc = formDescET.getText().toString();
            String notes = formNotesET.getText().toString();

            if (isRepeatable){
                if (daily.isCheckedIconVisible()){
//                if daily get time
//                    int hour = timePicker.getHour();
//                    int minute = timePicker.getMinute();

                } else if (weekly.isCheckedIconVisible()){
//                if weekly get day and time

                } else if (sched.isCheckedIconVisible()){
//                if schedule get date and time

                }
            } else {

            }
            finish();
        });
    }
    private String format2Digit(int num){
        String str = "0"+num;
        return str.substring(str.length()-2);
    }
}
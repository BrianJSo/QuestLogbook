package com.mobdeve.caim_sob.questlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
    private ChipGroup dayOfWeekSelector;
    boolean isRepeatable = false;

    private Calendar calendar;
    private int dayOfMonth;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int notificationID = 12;

    private QuestDBHelper questDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_maker_form);
        questDBHelper = new QuestDBHelper(this);
        createNotificationChannel();

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
        this.dayOfWeekSelector = findViewById(R.id.dayOfWeekSelector);

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
                Log.d("buboi", "inside isRepeatable");
                Chip questTypeChip = findViewById(questType.getCheckedChipId());
                Intent i = new Intent(QuestMakerForm.this, Notifications.class);
                i.putExtra(Notifications.NOTIFICATION_ID, notificationID);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(QuestMakerForm.this, notificationID, i, 0);
                notificationID++;
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1, 1000 * 5, pendingIntent);
                if (questTypeChip == daily){
//                if daily get time
                    Log.d("buboi", "inside daily");
                    int id = questDBHelper.insertQuestTemplateData(title, desc, notes, hour, minute);
                    if(id > -1)
                        i.putExtra(Notifications.QUEST_ID, id);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(QuestMakerForm.this, id, i, 0);
                    //testing if an alarm and instance is created every 20 seconds
//                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                            SystemClock.elapsedRealtime() + 1000,
//                            1000 * 20, pendingIntent);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);

                } else if (questTypeChip == weekly){
//                if weekly get day and time
                    Log.d("buboi", "inside weekly");
                    Chip chip = findViewById(dayOfWeekSelector.getCheckedChipId());
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(chip.getText().toString());
                    int id = questDBHelper.insertQuestTemplateData(title, desc, notes, hour, minute, dayOfWeek);
                    calendar.set(Calendar.DAY_OF_WEEK, dayToInt(dayOfWeek));
                    if(id > -1)
                        i.putExtra(Notifications.QUEST_ID, id);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(QuestMakerForm.this, id, i, 0);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);

                } else if (questTypeChip == sched){
//                if schedule get date and time
                    Log.d("buboi", "inside schedule");
                    int id = questDBHelper.insertQuestTemplateData(title, desc, notes, hour, minute, dayOfMonth, month, year);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.YEAR, year);
                    if(id > -1){
                        i.putExtra(Notifications.QUEST_ID, id);
                        Log.d("buboi", "time ni calendar" + calendar.getTimeInMillis());
                        //questDBHelper.templateToInstance(id);
                    }
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(QuestMakerForm.this, id, i, 0);
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                }
            } else {
                Log.d("buboi", "inside default");
                questDBHelper.insertQuestInstanceData(title, desc, notes);
            }
            finish();
        });
    }
    private String format2Digit(int num){
        String str = "0"+num;
        return str.substring(str.length()-2);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("brianso", "Quest Logbook", importance);
            channel.setDescription("Quest Logbook App");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private int dayToInt(DayOfWeek d){
        int i = 0;
        switch (d){
            case SUNDAY:
                i = 1;
                break;
            case MONDAY:
                i = 2;
                break;
            case TUESDAY:
                i = 3;
                break;
            case WEDNESDAY:
                i = 4;
                break;
            case THURSDAY:
                i = 5;
                break;
            case FRIDAY:
                i = 6;
                break;
            case SATURDAY:
                i = 7;
        }

        return i;
    }
}
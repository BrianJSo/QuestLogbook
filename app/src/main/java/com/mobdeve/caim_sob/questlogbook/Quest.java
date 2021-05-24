package com.mobdeve.caim_sob.questlogbook;

import java.time.LocalDateTime;
import java.time.LocalTime;

enum QuestType{
    DAILY, WEEKLY, SCHEDULE, QUICK
}

enum DayOfWeek{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

public class Quest {
    private int id;
    private String title;
    private String desc;
    private String notes;
    private QuestType type;
    private int hour;
    private int minute;
    private DayOfWeek dayOfWeek;
    private int dayOfMonth;
    private int month;
    private int year;

    // non repeatable quest constructor
    public Quest(int id, String title, String desc, String notes){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = QuestType.QUICK;
    }

    // daily constructor
    public Quest(int id, String title, String desc, String notes, int hour, int minute){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = QuestType.DAILY;
        this.hour = hour;
        this.minute = minute;
    }

    // weekly constructor
    public Quest(int id, String title, String desc, String notes, int hour, int minute, DayOfWeek dayOfWeek){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = QuestType.WEEKLY;
        this.hour = hour;
        this.minute = minute;
        this.dayOfWeek = dayOfWeek;
    }

    // scheduled quest constructor
    public Quest(int id, String title, String desc, String notes, int hour, int minute, int dayOfMonth, int month, int year){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = QuestType.SCHEDULE;
        this.hour = hour;
        this.minute = minute;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getNotes() {
        return notes;
    }

    public QuestType getType() {
        return type;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

package com.mobdeve.caim_sob.questlogbook;

import java.time.LocalDateTime;
import java.time.LocalTime;

enum QuestType{
    DAILY, WEEKLY, SCHEDULE, QUICK;
}

public class Quest {
    private int id;
    private String title;
    private String desc;
    private String notes;
    private QuestType type;
    private LocalTime onceTime;
    private LocalDateTime repeatTime;

    // quick create constructor
    public Quest(String title){
        this.id = 0;
        this.title = title;
        this.desc = "TODO";
        this.notes = "";
        this.type = QuestType.QUICK;
        this.onceTime = null;
        this.repeatTime= null;
    }

    // non repeatable quest form constructor
    public Quest(String title, String desc, String notes){
        this.id = 0;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = QuestType.QUICK;
        this.onceTime = null;
        this.repeatTime= null;
    }

    // daily and weekly constructor
    public Quest(String title, String desc, String notes, QuestType type, LocalTime onceTime){
        this.id = 0;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = type;
        this.onceTime = onceTime;
        this.repeatTime= null;
    }

    // scheduled quest constructor
    public Quest(String title, String desc, String notes, QuestType type, LocalDateTime repeatTime){
        this.id = 0;
        this.title = title;
        this.desc = desc;
        this.notes = notes;
        this.type = type;
        this.onceTime = null;
        this.repeatTime= repeatTime;
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

    public LocalTime getOnceTime() {
        return onceTime;
    }

    public LocalDateTime getRepeatTime() {
        return repeatTime;
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

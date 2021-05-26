package com.mobdeve.caim_sob.questlogbook;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestDBHelper extends SQLiteOpenHelper {

    public QuestDBHelper(Context context) {
        super(context, "QUESTS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table QuestInstances(" +
                "id INTEGER primary key, " +
                "title TEXT, " +
                "description TEXT, " +
                "notes TEXT, " +
                "type TEXT, " +
                "hour INTEGER, " +
                "minute INTEGER, " +
                "dayOfWeek TEXT, " +
                "dayOfMonth INTEGER, " +
                "month INTEGER, " +
                "year INTEGER)");
        db.execSQL("create Table QuestTemplates(" +
                "id INTEGER primary key, " +
                "title TEXT, " +
                "description TEXT, " +
                "notes TEXT, " +
                "type TEXT, " +
                "hour INTEGER, " +
                "minute INTEGER, " +
                "dayOfWeek TEXT, " +
                "dayOfMonth INTEGER, " +
                "month INTEGER, " +
                "year INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists QuestInstances");
        db.execSQL("drop Table if exists QuestTemplates");
    }

    public Boolean insertQuestInstanceData(String title, String description, String notes ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        contentValues.put("type", "QUICK");
        long result = db.insert("QuestInstances", null, contentValues);
        Log.d("BUBOI", String.valueOf(result));
        if (result>-1){
            return true;
        } else {
            return false;
        }
    }

    public Boolean templateToInstance(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestTemplates WHERE id=?", new String[] {strId});
        ContentValues contentValues = new ContentValues();
        if(cursor.moveToFirst()) {
            do {
                DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
            } while(cursor.moveToNext());
        }
        contentValues.remove("id");
        long result = db.insert("QuestInstances", null, contentValues);
        if (result>-1){
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertQuestTemplateData(String title, String description, String notes,
                                           int hour, int minute){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        contentValues.put("type", "DAILY");
        contentValues.put("hour", hour);
        contentValues.put("minute", minute);
        long result = db.insert("QuestTemplates", null, contentValues);
        if (result==1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertQuestTemplateData(String title, String description, String notes,
                                           int hour, int minute, DayOfWeek dayOfWeek){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        contentValues.put("type", "WEEKLY");
        contentValues.put("hour", hour);
        contentValues.put("minute", minute);
        contentValues.put("dayOfWeek", dayOfWeek.name());
        long result = db.insert("QuestTemplates", null, contentValues);
        if (result==1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertQuestTemplateData(String title, String description, String notes,
                                           int hour, int minute, int dayOfMonth, int month, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        contentValues.put("type", "SCHEDULE");
        contentValues.put("hour", hour);
        contentValues.put("minute", minute);
        contentValues.put("dayOfMonth", dayOfMonth);
        contentValues.put("month", month);
        contentValues.put("year", year);
        long result = db.insert("QuestTemplates", null, contentValues);
        if (result==1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateInstanceNotes(int id, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("notes", notes);
        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestTemplates WHERE id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.update("QuestInstances", contentValues, "id=?", new String[] {strId});
            if (result>0){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean updateTemplateNotes(int id, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("notes", notes);
        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestTemplates WHERE id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.update("QuestTemplates", contentValues, "id=?", new String[] {strId});
            if (result>0){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean updateQuestInstanceData( int id, String title, String description, String notes ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);

        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestInstances where id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.update("QuestInstances", contentValues, "id=?", new String[] {strId});
            if (result==1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteQuestInstanceData( int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestInstances where id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.delete("QuestInstances", "id=?", new String[] {strId});
            if (result>0){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Cursor getQuestInstances(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from QuestInstances", null);
        return cursor;
    }

    public Cursor getQuestTemplates(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from QuestTemplates", null);
        return cursor;
    }

    public Cursor getQuestTemplateByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestTemplates WHERE id=?", new String[] {strId});
        return cursor;
    }
}

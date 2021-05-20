package com.mobdeve.caim_sob.questlogbook;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestInstanceDBHelper extends SQLiteOpenHelper {

    public QuestInstanceDBHelper(Context context) {
        super(context, "QUESTS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table QuestInstances(" +
                "id INTEGER primary key," +
                "title TEXT, " +
                "description TEXT, " +
                "notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists QuestInstances");
    }

    public Boolean insertQuestInstanceData(String title, String description, String notes ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        long result = db.insert("QuestInstances", null, contentValues);
        Log.d("BUBOI", String.valueOf(result));
        if (result>-1){
            return true;
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

    public Boolean deleteQuestTemplateData( int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestInstances where id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.delete("QuestInstances", "id=?", new String[] {strId});
            if (result==1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from QuestInstances", null);
        return cursor;
    }

}

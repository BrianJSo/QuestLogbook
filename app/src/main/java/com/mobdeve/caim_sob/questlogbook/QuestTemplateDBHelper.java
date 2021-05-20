package com.mobdeve.caim_sob.questlogbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestTemplateDBHelper extends SQLiteOpenHelper {

    public QuestTemplateDBHelper(Context context) {
        super(context, "QUESTS.db", null, 1);

//        context,DATABASE_NAME,null,1
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table QuestTemplates(" +
                "title TEXT, " +
                "description TEXT, " +
                "notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists QuestTemplates");
    }

    public Boolean insertQuestTemplateData(String title, String description, String notes ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);
        long result = db.insert("QuestTemplates", null, contentValues);
        if (result==1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateQuestTemplateData( int id, String title, String description, String notes ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("notes", notes);

        String strId = String.valueOf(id);
        Cursor cursor = db.rawQuery("Select * from QuestTemplates where id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.update("QuestTemplates", contentValues, "id=?", new String[] {strId});
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
        Cursor cursor = db.rawQuery("Select * from QuestTemplates where id=?", new String[] {strId});
        if (cursor.getCount()>0){
            long result = db.delete("QuestTemplates", "id=?", new String[] {strId});
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
        Cursor cursor = db.rawQuery("Select * from QuestTemplates", null);
        return cursor;
    }

}

package com.mobdeve.caim_sob.questlogbook;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends BroadcastReceiver {

    private int notificationID = 12;
    public static String NOTIFICATION_ID = "notification_id";
    public static String QUEST_ID = "quest_id";
    private QuestDBHelper questDB;

    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "brianso");
            builder.setSmallIcon(R.drawable.quests);
            builder.setContentTitle("New Quest!");
            builder.setContentText("Quest title");
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            int x = intent.getIntExtra(NOTIFICATION_ID, 0);
            int id = intent.getIntExtra(QUEST_ID, 0);
            notificationManager.notify(x, builder.build());
            notificationID++;
            questDB = new QuestDBHelper(context);
            Log.d("buboi", "notif id: " + x);
            Log.d("buboi", "passed id: " + id);
            questDB.templateToInstance(id);
        //}
    }

}

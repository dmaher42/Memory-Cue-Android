package com.example.memorycue

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * BroadcastReceiver fired when the reminder alarm goes off. It constructs a notification with
 * a snooze action that schedules another reminder in five minutes.
 */
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val snoozeIntent = Intent(context, SnoozeReceiver::class.java)
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "memory_cue_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Memory Cue Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Memory Cue")
            .setContentText("Here's your reminder!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .addAction(0, "Snooze 5m", snoozePendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }
}
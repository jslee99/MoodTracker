package com.mobileprograming.moodtracker.ui.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mobileprograming.moodtracker.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        createNotificationChannel(context!!)
        notifyNotification(context!!)
    }

    private fun createNotificationChannel(context: Context) {

        // sdk 26이상부터는 채널이 필요함
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            // 채널 id, 이름, 중요도
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "기상 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            NotificationManagerCompat.from(context).createNotificationChannel( notificationChannel )
        }
    }

    private fun notifyNotification(context: Context){
        with(NotificationManagerCompat.from(context)){
            val builder = NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("알람")
                .setContentText("오늘 하루의 기분은 어떤가요?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object{
        private const val CHANNEL_ID = "1000"
        private const val NOTIFICATION_ID = 100
    }
}
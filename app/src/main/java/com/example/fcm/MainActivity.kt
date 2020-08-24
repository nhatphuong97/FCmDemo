package com.example.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult ->
            val token = instanceIdResult.token
            Log.i("FCM Token", token)
        } // Co may ao nao ho tro google api khong chu
        //anh coi build 1 cai thu a genymotion a // bua ni ma con choi gen //masy e yeu qua
        //k chay may ao kia dc


        if (mReciver != null) {
//Create an intent filter to listen to the broadcast sent with the action "ACTION_STRING_ACTIVITY"
            val intentFilter = IntentFilter(FCMService.BROADCAST_ACTION)
            //Map the intent filter to the receiver
            registerReceiver(mReciver, intentFilter)
        }
    }

    private val mReciver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == FCMService.BROADCAST_ACTION) {
                Toast.makeText(
                    applicationContext,
                    "received message in activity..!",
                    Toast.LENGTH_SHORT
                ).show()
                buildNotifiction(intent.getStringExtra("title") ?: "xxx")
            }

        }
    }

    private fun buildNotifiction(Message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val contentIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val b = NotificationCompat.Builder(this, "xxxx")

        b.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(android.R.drawable.ic_media_next)
            .setContentText(Message)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setContentIntent(contentIntent)
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            b.setStyle(NotificationCompat.BigTextStyle())
        } else {
            b.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getString(R.string.app_name))
            ).setContentTitle(getString(R.string.app_name))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.app_name)
            val notificationChannel = NotificationChannel(
                "xxxx",
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        notificationManager?.notify(1, b.build())
    }

}
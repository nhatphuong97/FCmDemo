package com.example.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val tokens =  FirebaseInstanceId.getInstance().token
        Log.d("xxx", "11123          $tokens")
    }

    override fun onMessageReceived(rM: RemoteMessage) {
        super.onMessageReceived(rM)
        Log.d("xxx", "11123          ${rM.notification?.body}")

    }

}
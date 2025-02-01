package com.example.firebasecloudmessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    companion object{
        const val TAG="FCM"
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG,"Token:${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification !=null){
            Log.d("FCM_MESSAGE","${message.notification?.title}")
            Log.d("FCM_MESSAGE","${message.notification?.body}")
            sendNotification(message.notification?.title, message.notification?.body)
        }
    }

    private fun sendNotification(title: String?, body: String?) {
        val channelId="My channel Id"
        val notifId=1
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel=NotificationChannel(channelId,"CH",NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification=NotificationCompat.Builder(this,channelId)
           .setSmallIcon(R.drawable.ic_notif)
           .setContentTitle(title)
           .setContentText(body)
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)
           .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(notifId,notification.build())
        }
    }
}
package com.mamak.geobaza.network.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.activity.ProjectDetailsActivity
import com.mamak.geobaza.utils.constans.AppConstans
import timber.log.Timber

class GeoBazaFirebaseMessagingService : FirebaseMessagingService() {
    private val adminChannelId = "admin_channel"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(this, ProjectDetailsActivity::class.java)
        intent.putExtra(AppConstans.EXTRA_PROJECT_NUMBER, 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this, getString(R.string.firebase_notification_channel_id))
            .setSmallIcon(R.mipmap.ic_launcher_new)
            .setSound(soundUri)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        val manager = NotificationManagerCompat.from(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(manager)
        }

        manager.notify(0, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.e(token)
        getSharedPreferences("_", Context.MODE_PRIVATE).edit().putString("fcm_token", token).apply()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManagerCompat: NotificationManagerCompat) {
        val adminChannelName = "New notification"
        val adminChannelDescription = "Admin channel"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(adminChannelId, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.BLACK
        adminChannel.enableVibration(true)
        notificationManagerCompat.createNotificationChannel(adminChannel)
    }



    companion object {
        fun getToken(context: Context): String? {
            return context.getSharedPreferences("_", Context.MODE_PRIVATE)
                    .getString("fcm_token", "empty")
        }
    }
}
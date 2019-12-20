package com.mamak.geobaza.network.firebase

import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mamak.geobaza.R
import timber.log.Timber

class GeoBazaFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this, getString(R.string.firebase_notification_channel_id))
            .setSmallIcon(R.mipmap.ic_launcher_new)
            .setSound(soundUri)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .build()
        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(0, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.e(token)
        getSharedPreferences("_", Context.MODE_PRIVATE).edit().putString("fcm_token", token).apply()
    }

    companion object {
        fun getToken(context: Context): String? {
            return context.getSharedPreferences("_", Context.MODE_PRIVATE)
                    .getString("fcm_token", "empty")
        }
    }
}
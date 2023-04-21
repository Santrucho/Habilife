package com.santrucho.habilife.ui.util.notifications


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.tasks.await


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val firebaseMessaging : FirebaseMessaging = FirebaseMessaging.getInstance()
        val token = firebaseMessaging.token.result
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!
            .body

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val notification: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(1, notification.build());

    }
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}
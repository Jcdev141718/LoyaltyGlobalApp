package com.loyaltyglobal.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.util.Constants.CHANNEL_ID
import com.loyaltyglobal.util.Constants.CHANNEL_NAME
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler
import org.json.JSONObject


class NotificationServiceExtension : OSRemoteNotificationReceivedHandler {
    private var notificationManager: NotificationManager? = null
    private var mNotificationCompatBuilder: NotificationCompat.Builder? = null
    private var defaultSoundUri: Uri? = null

    companion object {
        var mNotificationReceiveListener: NotificationReceiveListener? = null
    }

    init {
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }

    override fun remoteNotificationReceived(
        context: Context, notificationReceivedEvent: OSNotificationReceivedEvent
    ) {
        val notification = notificationReceivedEvent.notification
        if (notification.actionButtons != null) {
            for (button in notification.actionButtons) {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "ActionButton: $button")
            }
        }
        val mutableNotification = notification.mutableCopy()

        // If complete isn't call within a time period of 25 seconds, OneSignal internal logic will show the original notification
        notificationReceivedEvent.complete(null)
        val notificationPayload: JSONObject?
        Log.e("TAG","remoteNotificationReceived --> ${(notificationReceivedEvent.notification)}")
//        notificationPayload =
//            JSONObject(notificationReceivedEvent.notification.additionalData.toString())
        showNotification(notificationReceivedEvent, context)
    }

    private fun showNotification(
        remoteMessage: OSNotificationReceivedEvent, context: Context
    ) {
        val messageBody = remoteMessage.notification?.body
        val title = remoteMessage.notification?.title
        val largeImage: Bitmap? = null
        val notificationType: String?

        notificationType =
            remoteMessage.notification.additionalData?.get("notification_type").toString()

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent? =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mNotificationCompatBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_NAME,
                context.resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager?.createNotificationChannel(notificationChannel)
            NotificationCompat.Builder(context, CHANNEL_NAME)
                .setDefaults(Notification.DEFAULT_VIBRATE).setContentTitle(title)
                .setContentText(messageBody).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true).setContentIntent(pendingIntent)
        } else {
            commonNotification(
                context,
                title,
                remoteMessage.notification?.body,
                pendingIntent!!,
                defaultSoundUri!!,
                largeImage,
                notificationType
            )
        }
        notificationManager?.notify(10000001, mNotificationCompatBuilder?.build())

    }

    private fun commonNotification(
        context: Context,
        title: String?,
        messageBody: String?,
        pendingIntent: PendingIntent,
        defaultSoundUri: Uri,
        largeImage: Bitmap?,
        notificationType: String,
    ): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title)
                .setDefaults(NotificationCompat.DEFAULT_SOUND).setContentText(messageBody)
                .setSmallIcon(R.drawable.ic_notification).setChannelId(CHANNEL_NAME)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
                .setContentIntent(pendingIntent).setSound(defaultSoundUri)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        } else {
            NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title)
                .setContentText(messageBody).setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
                .setContentIntent(pendingIntent).setLargeIcon(
                    BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
                )
        }
    }
}
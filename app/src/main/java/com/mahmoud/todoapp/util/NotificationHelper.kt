package com.mahmoud.todoapp.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.mahmoud.todoapp.DetailsEventsActivity
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.receiver.AlarmReceiver
import com.mahmoud.todoapp.util.Constants.NOTIFICATION_TIME
import com.mahmoud.todoapp.util.DateHelper.getFormatTime
import kotlinx.coroutines.*


class NotificationHelper(base: Context?) : ContextWrapper(base) {

    val channelID = "channelID"
    val channelName = "Channel Name"
    private var mManager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        getManager()!!.createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager? {
        if (mManager == null) {
            mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager
    }

    fun createNotification(title: String?, isCustom: Boolean = false): NotificationCompat.Builder {

        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.profile_demo)

        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(getFormatTime())
            .setContentText(title)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_bell)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setWhen(System.currentTimeMillis())

        if (isCustom) {
            return builder.handleCustomNotification( true)

        }

        builder.setLargeIcon(bitmap)
            .addActions(DetailsEventsActivity::class.java)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap).bigLargeIcon(null)
                    .setBigContentTitle("BigContentTitle")
                    .setSummaryText("SummaryText")
            )

        return builder


    }


    private fun notificationVibrator() {
        val v: Vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else
            v.vibrate(200)
    }

    private fun NotificationCompat.Builder.addActions(activity: Class<*>): NotificationCompat.Builder {
        val intent = Intent(applicationContext, activity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val dismissIntent = Intent(applicationContext, AlarmReceiver::class.java)
        dismissIntent.putExtra("cancel", true)
        val dismissPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(applicationContext, 1, dismissIntent, 0)

        this.addAction(R.drawable.ic_icons_dark_x, getString(R.string.cancel), dismissPendingIntent)
        return this.addAction(
            R.drawable.ic_events,
            applicationContext.resources.getString(R.string.details),
            pendingIntent
        )
    }

    private fun allowVibrateAndSoundNotification(
        isVibrate: Boolean = true,
        isSound: Boolean = true
    ) {

        if (isVibrate)
            notificationVibrator()
        if (isSound) {
            val ring = NotificationSound.getInstance(applicationContext).getRingToneInstance()
            ring?.play()
        }

    }


    private fun NotificationCompat.Builder.handleCustomNotification(
        isExpand: Boolean = true
    ): NotificationCompat.Builder {

        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification_item)
        setCustomContentView(notificationLayout)
        handleExpandNotification()
        if (!isExpand) {
            setCustomBigContentView(null)
            notificationLayout.setViewVisibility(R.id.imgArrowExpand, View.GONE)


            val detailsIntent =
                Intent(applicationContext, DetailsEventsActivity::class.java).apply {
                    putExtra("cancel", true)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                detailsIntent,
                PendingIntent.FLAG_ONE_SHOT
            )
            notificationLayout.setOnClickPendingIntent(
                R.id.notificationContainer,
                pendingIntent
            )

        }
        this.color = resources.getColor(R.color.colorAccent, resources.newTheme())


        notificationLayout.setTextViewText(R.id.tvNotificationTitle, "Title")
        notificationLayout.setTextViewText(R.id.tvNotificationInfo, "Expand for more information")
        notificationLayout.setTextViewText(
            R.id.tvNotificationTime,
            getFormatTime()
        )

        notificationLayout.setImageViewResource(
            R.id.imgNotificationSmall,
            R.drawable.ic_calendar_menu
        )


        return this

    }


    private fun NotificationCompat.Builder.handleExpandNotification(): NotificationCompat.Builder {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.profile_demo)

        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.expnad_notification_item)
        setCustomBigContentView(notificationLayoutExpanded)
        notificationLayoutExpanded.setImageViewBitmap(R.id.imgNotification, bitmap)
        notificationLayoutExpanded.setTextViewText(
            R.id.notification_expanded_title,
            "My expand title"
        )
        notificationLayoutExpanded.setTextViewText(
            R.id.notification_expanded_info,
            "My expand Info"
        )

        val detailsIntent = Intent(applicationContext, DetailsEventsActivity::class.java).apply {
            putExtra("cancel", true)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            detailsIntent,
            PendingIntent.FLAG_ONE_SHOT
        )


        val dismissIntent = Intent(applicationContext, AlarmReceiver::class.java)
        dismissIntent.putExtra("cancel", true)
        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.btnNotificationDetails,
            pendingIntent
        )
        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.imgNotification,
            pendingIntent
        )

        val dismissPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(applicationContext, 1, dismissIntent, 0)
        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.btnNotificationCancel,
            dismissPendingIntent
        )


        return this
    }

}
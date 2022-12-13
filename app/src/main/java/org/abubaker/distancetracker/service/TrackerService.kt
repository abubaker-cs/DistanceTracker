package org.abubaker.distancetracker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import org.abubaker.distancetracker.util.Constants.ACTION_SERVICE_START
import org.abubaker.distancetracker.util.Constants.ACTION_SERVICE_STOP
import org.abubaker.distancetracker.util.Constants.NOTIFICATION_CHANNEL_ID
import org.abubaker.distancetracker.util.Constants.NOTIFICATION_CHANNEL_NAME
import javax.inject.Inject

@AndroidEntryPoint
class TrackerService : LifecycleService() {

    // Injecting Notification + NotificationManager
    @Inject
    lateinit var notification: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    companion object {
        var started = MutableLiveData<Boolean>()
    }

    private fun setInitialValues() {
        started.postValue(false)
    }

    override fun onCreate() {

        // Set the initial values
        setInitialValues()

        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {

            when (it.action) {

                // Start
                ACTION_SERVICE_START -> {
                    started.postValue(true)
                }

                // Stop
                ACTION_SERVICE_STOP -> {
                    started.postValue(false)
                }

                else -> {
                    // Do nothing
                }

            }

        }

        return super.onStartCommand(intent, flags, startId)

    }

    private fun createNotificationChannel() {

        // Check if the device is running API Level 26+ | Android 8.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Configurations for the Notification Channel
            val channel = NotificationChannel(

                // tracker_notification_id
                NOTIFICATION_CHANNEL_ID,

                // tracker_notification
                NOTIFICATION_CHANNEL_NAME,

                // Low importance
                NotificationManager.IMPORTANCE_LOW
            )

            // Create the channel
            notificationManager.createNotificationChannel(channel)

        }
    }


}

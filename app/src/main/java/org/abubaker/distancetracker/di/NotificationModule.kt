package org.abubaker.distancetracker.di

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import org.abubaker.distancetracker.MainActivity
import org.abubaker.distancetracker.R
import org.abubaker.distancetracker.util.Constants.NOTIFICATION_CHANNEL_ID
import org.abubaker.distancetracker.util.Constants.PENDING_INTENT_REQUEST_CODE

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    // Function to create a notification
    @SuppressLint("UnspecifiedImmutableFlag")
    @ServiceScoped // Scoped to our tracker service
    @Provides // This is used for classes that we do not own, so dagger library will know how to provide them
    fun providePendingIntent(
        @ApplicationContext context: Context
    ): PendingIntent {

        // Only if the SDK is greater than 26
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            PendingIntent.getActivity(

                // Context
                context,

                // Actual request code = 99
                PENDING_INTENT_REQUEST_CODE,

                // Intent = To open the MainActivity
                Intent(context, MainActivity::class.java),

                // Flag = Mutable or Update the current
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        } else {

            PendingIntent.getActivity(

                // Context
                context,

                // Actual request code = 99
                PENDING_INTENT_REQUEST_CODE,

                // Intent = To open the MainActivity
                Intent(context, MainActivity::class.java),

                // Flag = To update the current intent
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        }
    }

    // Configuration for the custom notification
    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {

        // Create a notification builder
        return NotificationCompat.Builder(

            // Current context
            context,

            // tracker_notification_id
            NOTIFICATION_CHANNEL_ID
        )

            // Auto Cancel = False
            .setAutoCancel(false)

            // Ongoing notifications do not have X close button and are not affected by "Clear all" button
            .setOngoing(true)

            // Runner icon
            .setSmallIcon(R.drawable.ic_run)

            // This pending intent will be used to launch our MapsFragment
            .setContentIntent(pendingIntent)

    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {

        // Return the notification manager
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

}

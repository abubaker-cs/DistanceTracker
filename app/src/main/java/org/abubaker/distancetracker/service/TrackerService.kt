package org.abubaker.distancetracker.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import org.abubaker.distancetracker.util.Constants.ACTION_SERVICE_START
import org.abubaker.distancetracker.util.Constants.ACTION_SERVICE_STOP

@AndroidEntryPoint
class TrackerService : LifecycleService() {

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


}

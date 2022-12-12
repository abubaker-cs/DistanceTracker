package org.abubaker.distancetracker.util

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import org.abubaker.distancetracker.util.Constants.PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE
import org.abubaker.distancetracker.util.Constants.PERMISSION_LOCATION_REQUEST_CODE

object Permissions {

    /**
     * 01 Fine + Coarse
     */

    // Do we have the permission to access the location using Fine + Coarse?
    fun hasLocationPermission(context: Context) = EasyPermissions.hasPermissions(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
        // android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // Request the permission to access the location using Fine + Coarse
    fun requestLocationPermission(fragment: Fragment) {

        EasyPermissions.requestPermissions(
            fragment,
            "This application cannot work without Location Permission",
            PERMISSION_LOCATION_REQUEST_CODE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            // android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

    }

    /**
     * 02 Background Location Permission
     */

    // Do we have the background location permission?
    fun hasBackgroundLocationPermission(context: Context): Boolean {

        // If our Android has API Level 29+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return EasyPermissions.hasPermissions(
                context,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

        return true
    }

    // Request the background location permission
    fun requestBackgroundLocationPermission(fragment: Fragment) {

        // If our Android has API Level 29+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                fragment,
                "Background location permission is essential to this application. Without it we will not be able to provide you with our service.",
                PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    }


}

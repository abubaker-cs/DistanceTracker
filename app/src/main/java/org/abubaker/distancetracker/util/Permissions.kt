package org.abubaker.distancetracker.util

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import org.abubaker.distancetracker.util.Constants.PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE
import org.abubaker.distancetracker.util.Constants.PERMISSION_LOCATION_REQUEST_CODE

object Permissions {

    // Function to check if we have the permission
    fun hasLocationPermission(context: Context) = EasyPermissions.hasPermissions(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )


    // Function to request the permission
    fun requestLocationPermission(fragment: Fragment) {

        EasyPermissions.requestPermissions(
            fragment,
            "This application cannot work without Location Permission",
            PERMISSION_LOCATION_REQUEST_CODE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

    }


    // Function to check if we have the permission
    fun hasBackgroundLocationPermission(context: Context): Boolean {

        // Only if API LEVEL 29+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return EasyPermissions.hasPermissions(
                context,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

        return true
    }

    fun requestBackgroundLocationPermission(fragment: Fragment) {

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

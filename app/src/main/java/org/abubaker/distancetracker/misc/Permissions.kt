package org.abubaker.distancetracker.misc

import android.content.Context
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import org.abubaker.distancetracker.misc.Constants.PERMISSION_LOCATION_REQUEST_CODE

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

}

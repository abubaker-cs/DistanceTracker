package org.abubaker.distancetracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.abubaker.distancetracker.databinding.FragmentMapsBinding
import org.abubaker.distancetracker.util.ExtensionFunctions.disable
import org.abubaker.distancetracker.util.ExtensionFunctions.hide
import org.abubaker.distancetracker.util.ExtensionFunctions.show
import org.abubaker.distancetracker.util.Permissions.hasBackgroundLocationPermission
import org.abubaker.distancetracker.util.Permissions.requestBackgroundLocationPermission


class MapsFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    EasyPermissions.PermissionCallbacks {

    // Binding Object: FragmentMapsBinding
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var cameraPosition: CameraPosition

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate: @layout/fragment_maps.xml
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        // Specify the current activity as the lifecycle owner
        binding.lifecycleOwner = viewLifecycleOwner

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.apply {

            // Button: Start
            btnStart.setOnClickListener {
                onStartButtonClicked()
            }

            // Button: Stop
            btnStop.setOnClickListener {
                onStopButtonClicked()
            }

            // Button: Reset
            btnReset.setOnClickListener {
                onResetButtonClicked()
            }

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Build the map.
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // Assigning the map
        map = googleMap

        // This will display the MY LOCATION BUTTON on the map
        map.isMyLocationEnabled = true

        // important to enable this feature
        map.setOnMyLocationButtonClickListener(this)

        map.uiSettings.apply {

            // Disabling these functionalities

            // Static Image View
            // ==============================================
            // 1. Fixed Position: Disables camera movement
            // 2. Disabled Zoom functionality
            // isScrollGesturesEnabled = false
            // isZoomGesturesEnabled = false
            isZoomControlsEnabled = false

            // My Location
            // * Requires enabling my-location layer
            // ==============================================
            isMyLocationButtonEnabled = true

            // Enables: Zoom +/- buttons on the map
            isZoomGesturesEnabled = false

            isRotateGesturesEnabled = false

            isTiltGesturesEnabled = false

            // on Rotation
            isCompassEnabled = false

            // onPress Toolbar
            // ==============================================
            // 1. Go to Google Maps
            // 2. Find Direction
            isMapToolbarEnabled = false

            isScrollGesturesEnabled = false

        }


    }

    // Button: Start
    private fun onStartButtonClicked() {

        // We are checking if we have background location permission
        if (hasBackgroundLocationPermission(requireContext())) {

            // This will display the MY LOCATION BUTTON on the map
            map.isMyLocationEnabled = true

            Log.d("MapsFragment", "Already Enabled")

            binding.btnStart.disable()
            binding.btnStart.hide()
            binding.btnStop.show()

        } else {

            //  We are requesting background location permission
            requestBackgroundLocationPermission(this)

        }
    }

    // Button: Stop
    private fun onStopButtonClicked() {
        binding.btnStop.hide()
        binding.btnStart.show()
    }


    // Button: Reset
    private fun onResetButtonClicked() {

    }

    // This method is called when the user clicks the My Location button on the map.
    override fun onMyLocationButtonClick(): Boolean {

        //  map.isMyLocationEnabled = true
        // map.uiSettings.isMyLocationButtonEnabled = true


        // TODO Move the camera to the location with ZOOM Level
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(34.04692127928215, -118.24748421830992), 10f))

        binding.tvHint.animate().alpha(0f).duration = 1500

        lifecycleScope.launch {
            delay(2500)
            binding.tvHint.hide()
            binding.btnStart.show()
        }

        return false

    }

    // This method is called when the user has accepted (or denied) our permission request.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // This will redirect the user to his current location
        map.isMyLocationEnabled = true

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // Permanently Denied?
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        // If some permissions were permanently denied
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

            // Open settings dialog so user can enable permissions
            SettingsDialog.Builder(requireActivity()).build().show()

        } else {

            // Request permissions again
            requestBackgroundLocationPermission(this)

        }
    }

    // Permission Granted
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

        onStartButtonClicked()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null, to avoid memory leaks
        _binding = null

    }

}

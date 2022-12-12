package org.abubaker.distancetracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
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

    val started = MutableLiveData(false)

    private var startTime = 0L
    private var stopTime = 0L

    private var locationList = mutableListOf<LatLng>()
    private var polylineList = mutableListOf<Polyline>()
    private var markerList = mutableListOf<Marker>()

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

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap

        // We also need to call our google map object to enable the current location button
        map.isMyLocationEnabled = true

        // important to enable this feature
        map.setOnMyLocationButtonClickListener(this)

        map.uiSettings.apply {

            // Disabling these functionalities
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isScrollGesturesEnabled = false

        }


    }

    // Button: Start
    private fun onStartButtonClicked() {

        // We are checking if we have background location permission
        if (hasBackgroundLocationPermission(requireContext())) {

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
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

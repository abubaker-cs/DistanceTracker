package org.abubaker.distancetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.abubaker.distancetracker.databinding.FragmentMapsBinding
import org.abubaker.distancetracker.util.ExtensionFunctions.hide
import org.abubaker.distancetracker.util.ExtensionFunctions.show

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    // Binding Object: FragmentMapsBinding
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate: @layout/fragment_maps.xml
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        // Specify the current activity as the lifecycle owner
        binding.lifecycleOwner = this

        binding.apply {

            // Button: Start
            btnStart.setOnClickListener {

            }

            // Button: Stop
            btnStop.setOnClickListener {

            }

            // Button: Reset
            btnReset.setOnClickListener {

            }


        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googlemap: GoogleMap) {

        map = googlemap

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

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null, to avoid memory leaks
        _binding = null

    }

    override fun onMyLocationButtonClick(): Boolean {

        binding.tvHint.animate().alpha(0f).duration = 1500

        lifecycleScope.launch {
            delay(2500)
            binding.tvHint.hide()
            binding.btnStart.show()
        }

        return false

    }


}

package org.abubaker.distancetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.abubaker.distancetracker.R
import org.abubaker.distancetracker.databinding.ActivityMainBinding
import org.abubaker.distancetracker.util.Permissions.hasLocationPermission

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate Layout: @layout/activity_main.xml
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //
        binding.lifecycleOwner = this

        // Find the Navigation Controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        // Get the Navigation Controller
        val navController = navHostFragment.navController

        // Check if we have the permissions, then navigate to the main fragment
        if (hasLocationPermission(this)) {

            // Navigate to the Tracking Fragment
            navController.navigate(R.id.action_permissionFragment_to_mapsFragment)
        }


    }

}

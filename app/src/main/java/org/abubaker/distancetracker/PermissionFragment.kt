package org.abubaker.distancetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import org.abubaker.distancetracker.databinding.FragmentPermissionBinding
import org.abubaker.distancetracker.misc.Permissions.hasLocationPermission
import org.abubaker.distancetracker.misc.Permissions.requestLocationPermission

class PermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentPermissionBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate: @layout/fragment_permission.xml
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_permission, container, false)

        // Specify the current activity as the lifecycle owner
        binding.lifecycleOwner = this

        // Button: Continue
        binding.btnContinue.setOnClickListener {

            if (hasLocationPermission(requireContext())) {

                // Navigate to the main screen
                findNavController().navigate(R.id.action_permissionFragment_to_mapsFragment)

            } else {

                // Ask the user to grant permissions
                requestLocationPermission(this)

            }

        }

        return binding.root

    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *
     *
     * @param requestCode The request code passed in [.requestPermissions].
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either [android.content.pm.PackageManager.PERMISSION_GRANTED]
     * or [android.content.pm.PackageManager.PERMISSION_DENIED]. Never null.
     *
     * @see .requestPermissions
     */
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        // Check if granting access to the Fine Location was denied
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

            // Show the dialog to the user
            SettingsDialog.Builder(requireContext()).build().show()

        } else {
            requestLocationPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

        // Navigate to the main screen
        findNavController().navigate(R.id.action_permissionFragment_to_mapsFragment)

    }

    /**
     * Called when the view previously created by [.onCreateView] has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after [.onStop] and before [.onDestroy].  It is called
     * *regardless* of whether [.onCreateView] returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null
        _binding = null

    }

}

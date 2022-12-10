package org.abubaker.distancetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.abubaker.distancetracker.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate: @layout/fragment_result.xml
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)

        // Specify the current activity as the lifecycle owner
        binding.lifecycleOwner = this

        return binding.root

    }

}

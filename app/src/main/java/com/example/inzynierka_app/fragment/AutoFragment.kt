package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentAutoBinding
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity())[GripperViewModel::class.java]

        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.timerBaseTime.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.chStopWatch.base = it
            }
        }

        viewModel.isTimerRunning.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    binding.chStopWatch.start()
                } else
                    binding.chStopWatch.stop()
            }
        }

        val adapter = ArrayAdapter(
            requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.start_point)
        )
        binding.actvStartPoint.setAdapter(adapter)
        binding.actvStartPoint.setOnItemClickListener { parent, _, pos, _ ->
            viewModel.writeStartPoint(parent.getItemAtPosition(pos).toString())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
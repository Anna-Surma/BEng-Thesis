package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

        viewModel.timerBaseTime.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.chStopWatch.base = it
            }
        }

        viewModel.isTimerRunning.observe(viewLifecycleOwner) {
            if (it != null) {
                if(it) {
                    binding.chStopWatch.start()
                }
                else
                    binding.chStopWatch.stop()
            }
        }

//        binding.etCycles.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                viewModel.cycleOrTimeCheck()
//            }
//        }
//        binding.etTime.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                viewModel.cycleOrTimeCheck()
//            }
//        }

        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this

  //      binding.etTime.inputType = TYPE_INTEGER

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
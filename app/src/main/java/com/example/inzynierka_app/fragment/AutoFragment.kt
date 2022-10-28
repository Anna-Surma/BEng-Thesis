package com.example.inzynierka_app.fragment

import android.content.RestrictionEntry.TYPE_INTEGER
import android.content.RestrictionEntry.TYPE_NULL
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.Timer
import com.example.inzynierka_app.databinding.FragmentAutoBinding
import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!
    private var timer = Timer()

    private lateinit var viewModel: GripperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this
        // var start_point = binding.sStart.selectedItem.toString()

        binding.btnStartButton.setOnClickListener {
            if(viewModel.controlActive.value == true){
                if (viewModel.autoMode.value == false) {
                    if (viewModel.isPause.value == false){
                        viewModel.resetCycles()
                        timer.offset = 0
                        binding.chStopWatch.base = timer.setBaseTime()
                    }
                    viewModel.startAuto()
                    viewModel.startReadCycles(Params("\"Data\".licznik_cykli"))

                    binding.chStopWatch.base = timer.setBaseTime()
                    binding.chStopWatch.start()

                    binding.etCycles.inputType = TYPE_NULL
                }
            }
        }

        binding.btnStopButton.setOnClickListener {
            viewModel.stopAuto()

            binding.chStopWatch.stop()
            viewModel.stopReadCycles()

            binding.etCycles.inputType = TYPE_INTEGER
        }

        binding.btnPauseButton.setOnClickListener {
            if (viewModel.isRunning.value == true && viewModel.isPause.value == false) {
                viewModel.pause()
                timer.offset = timer.getElapsedRealtime() - binding.chStopWatch.base
                binding.chStopWatch.stop()

                binding.etCycles.inputType = TYPE_INTEGER
            }
        }

        viewModel.controlActive.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.controlActive.value == true) {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_control", true))
                } else {
                        viewModel.writeData(ParamsWriteVar("\"Data\".app_control", false))
                        viewModel.writeData(ParamsWriteVar("\"Data\".app_auto", false))
                    binding.etCycles.inputType = TYPE_INTEGER
                }
            }
        }

        viewModel.autoMode.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.controlActive.value == true) {
                    if (viewModel.autoMode.value == true) {
                        viewModel.writeData(ParamsWriteVar("\"Data\".app_auto", true))
                    } else
                        viewModel.writeData(ParamsWriteVar("\"Data\".app_auto", false))
                } else {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_auto", false))
                }
            }
        }

        viewModel.readData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.toString() != binding.tvCyclesNumber.text) {
                    binding.tvCyclesNumber.text = it.toString()
                }
            }
        }

        viewModel.reachSetCycles.observe(viewLifecycleOwner) {
            if (it != null) {
                if(it == true)
                binding.chStopWatch.stop()
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopReadCycles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
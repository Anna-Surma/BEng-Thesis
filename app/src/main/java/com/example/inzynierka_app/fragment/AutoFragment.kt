package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.databinding.FragmentAutoBinding
import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel

    //TODO move to ViewModel
    var running = false
    var offset: Long = 0

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
            if (viewModel.autoMode.value == false) {
                viewModel.startAuto()
            }

            viewModel.resetCycles()
            viewModel.startReadCycles(Params("\"Data\".licznik_cykli"))

            if (!running) {
                setBaseTime()
                binding.chStopWatch.start()
                running = true
            }
        }

        binding.btnStopButton.setOnClickListener {
            viewModel.stopAuto()
            offset = 0
            setBaseTime()
            viewModel.stopReadCycles()
        }

        binding.btnPauseButton.setOnClickListener {
            //   viewModel.pauseAuto()
            if (running) {
                saveOffset()
                binding.chStopWatch.stop()
                running = false
            }
        }

        viewModel.controlActive.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.controlActive.value == true) {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_control", true))
                    Log.i("Auto", "Auto control active")
                } else {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_control", false))
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
                    Log.i("Auto", "control false")
                }
            }
        }

        viewModel.resetCycles.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.resetCycles.value == true) {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", true))
                    viewModel.stopResetCycles()
                } else {
                    viewModel.writeData(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", false))
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
        return view
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopReadCycles()
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.chStopWatch.base
    }

    private fun setBaseTime() {
        binding.chStopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
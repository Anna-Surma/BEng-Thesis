package com.example.inzynierka_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentManualBinding
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManualFragment : Fragment() {

    private var _binding: FragmentManualBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentManualBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)
        binding.btnStep.setOnClickListener {
            viewModel.startStep()
        }

        viewModel.manualMode.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.controlActive.value == true) {
                    if (viewModel.manualMode.value == true) {
                        viewModel.writeData(ParamsWriteVar("\"Data\".mb_app_step", true))
                        viewModel.stopStep()
                    } else
                        viewModel.writeData(ParamsWriteVar("\"Data\".mb_app_step", false))
                } else
                    viewModel.writeData(ParamsWriteVar("\"Data\".mb_app_step", false))
            }
        }

        viewModel.stepsArrayResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                for (nr in it) {
                    if (nr.result) {
                        when (nr.id) {
                            1 -> {binding.tvStepNr.text = getString(R.string.step_1)
                            binding.ivStepImage.setImageResource(R.drawable.right_up_open)}
                            2 -> {binding.tvStepNr.text = getString(R.string.step_2)
                                binding.ivStepImage.setImageResource(R.drawable.right_down_open)}
                            3 -> {binding.tvStepNr.text = getString(R.string.step_3)
                                binding.ivStepImage.setImageResource(R.drawable.right_down_close)}
                            4 -> {binding.tvStepNr.text = getString(R.string.step_4)
                                binding.ivStepImage.setImageResource(R.drawable.right_up_close)}
                            5 -> {binding.tvStepNr.text = getString(R.string.step_5)
                                binding.ivStepImage.setImageResource(R.drawable.left_up_close)}
                            6 -> {binding.tvStepNr.text = getString(R.string.step_6)
                                binding.ivStepImage.setImageResource(R.drawable.left_down_close)}
                            7 -> {binding.tvStepNr.text = getString(R.string.step_7)
                                binding.ivStepImage.setImageResource(R.drawable.left_down_open)}
                            8 -> {binding.tvStepNr.text = getString(R.string.step_8)
                                binding.ivStepImage.setImageResource(R.drawable.left_up_open)}
                            else -> {binding.tvStepNr.text = getString(R.string.step_1)
                                binding.ivStepImage.setImageResource(R.drawable.left_down_close)}
                        }
                    }
                }
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.inzynierka_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.transition.R
import com.example.inzynierka_app.databinding.FragmentManualBinding
import com.example.inzynierka_app.model.ParamsWrite
import com.example.inzynierka_app.ui.viewmodel.GripperViewModel
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

        viewModel = ViewModelProvider(requireActivity())[(GripperViewModel::class.java)]

        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnStep.setOnClickListener {
            viewModel.startStep()
        }
        viewModel.manualMode.observe(viewLifecycleOwner) {
            if (it != null) {
                if (viewModel.controlActive.value == true) {
                    if (viewModel.manualMode.value == true) {
                        viewModel.writeSingleData(ParamsWrite("\"DB100\".mb_app_step", true))
                        viewModel.stopStep()
                    } else
                        viewModel.writeSingleData(ParamsWrite("\"DB100\".mb_app_step", false))
                } else
                    viewModel.writeSingleData(ParamsWrite("\"DB100\".mb_app_step", false))
            }
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            com.example.inzynierka_app.R.array.start_point))
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
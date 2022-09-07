package com.example.inzynierka_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.databinding.FragmentAutoBinding
import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.viewmodel.AutoViewModel

class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AutoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(AutoViewModel::class.java)

        binding.activeButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                viewModel.activeControl()
            }
            else {
                viewModel.deactiveControl()
                viewModel.stopAuto()
            }
        }

        binding.startButton.setOnClickListener {
            viewModel.startAuto()
            viewModel.resetCycles()
            viewModel.readData(Params("\"Data\".licznik_cykli"))
        }

        binding.stopButton.setOnClickListener {
            viewModel.stopAuto()
        }

        viewModel.controlActive.observe(viewLifecycleOwner) {
            if (it != null) {
                if(viewModel.controlActive.value == true){
                    viewModel.write_data(ParamsWriteVar("\"Data\".app_control", true))
                }
                else{
                    viewModel.write_data(ParamsWriteVar("\"Data\".app_control", false))
                }
            }
        }

        viewModel.autoMode.observe(viewLifecycleOwner) {
            if (it != null) {
                if(viewModel.controlActive.value == true){
                    if(viewModel.autoMode.value == true){
                        viewModel.write_data(ParamsWriteVar("\"Data\".app_auto", true))
                    }
                    else
                        viewModel.write_data(ParamsWriteVar("\"Data\".app_auto", false))
                }
                else{
                    viewModel.write_data(ParamsWriteVar("\"Data\".app_auto", false))
                }
            }
        }

        viewModel.resetCycles.observe(viewLifecycleOwner) {
            if (it != null){
                if (viewModel.resetCycles.value == true){
                    viewModel.write_data(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", true))
                    viewModel.stopResetCycles()
                }
                else{
                    viewModel.write_data(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", false))
                }
            }
        }

        viewModel.readData.observe(viewLifecycleOwner) {
            if (it != null){
                if(it.toString() != binding.cyclesNumber.text){
                    binding.cyclesNumber.text = it.toString()
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
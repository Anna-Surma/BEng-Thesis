package com.example.inzynierka_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.databinding.FragmentManualBinding
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.viewmodel.ManualViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManualFragment : Fragment() {

    private var _binding: FragmentManualBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ManualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentManualBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(ManualViewModel::class.java)

        binding.stepButton.setOnClickListener {
            viewModel.startStep()
        }

        viewModel.manualMode.observe(viewLifecycleOwner) {
            if (it != null) {
                    if(viewModel.manualMode.value == true){
                        viewModel.write_data(ParamsWriteVar("\"Data\".app_krok", true))
                        viewModel.stopStep()
                    }
                    else
                        viewModel.write_data(ParamsWriteVar("\"Data\".app_krok", false))
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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

    private lateinit var read_param: Params
    private lateinit var write_param: ParamsWriteVar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(AutoViewModel::class.java)

        read_param = Params("\"Data\".Random_Int")
        write_param = ParamsWriteVar("\"Data\".Random_Int", 7)
        binding.getButton.setOnClickListener {
            viewModel.readData(read_param)
            viewModel.write_data(write_param)
        }

        viewModel.readData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.PLCData.text = viewModel.readData.value.toString()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
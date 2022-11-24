package com.example.inzynierka_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.databinding.FragmentBlockBinding

import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ReadDataRequest
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockFragment : Fragment() {

    private var _binding: FragmentBlockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel

    private lateinit var array: ArrayList<ReadDataRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBlockBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

        array = arrayListOf(
            ReadDataRequest(1, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_1")),
            ReadDataRequest(2, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_2")),
            ReadDataRequest(3, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_3")),
            ReadDataRequest(4, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_4")),
            ReadDataRequest(5, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_5")),
            ReadDataRequest(6, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_6")),
            ReadDataRequest(7, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_7")),
            ReadDataRequest(8, "2.0", "PlcProgram.Read", Params("\"Data\".mb_step_8"))
        )
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
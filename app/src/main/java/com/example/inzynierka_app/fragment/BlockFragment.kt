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
import com.example.inzynierka_app.viewmodel.BlockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockFragment : Fragment() {

    private var _binding: FragmentBlockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BlockViewModel

    private lateinit var array: ArrayList<ReadDataRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlockBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(BlockViewModel::class.java)

        array = arrayListOf(
            ReadDataRequest(1, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_1")),
            ReadDataRequest(2, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_2")),
            ReadDataRequest(3, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_3")),
            ReadDataRequest(4, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_4")),
            ReadDataRequest(5, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_5")),
            ReadDataRequest(6, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_6")),
            ReadDataRequest(7, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_7")),
            ReadDataRequest(8, "2.0", "PlcProgram.Read", Params("\"Data\".app_KROK_8"))
        )

        viewModel.readData(array)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inzynierka_app.adapter.BlockStepAdapter
import com.example.inzynierka_app.adapter.ErrorAdapter
import com.example.inzynierka_app.databinding.FragmentBlockBinding
import com.example.inzynierka_app.model.ReadDataRequest
import com.example.inzynierka_app.model.StepItem
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockFragment : Fragment() {

    private var _binding: FragmentBlockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel
    private lateinit var stepAdapter: BlockStepAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBlockBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this


        setupRecyclerView()

        viewModel.blockSteps.observe(viewLifecycleOwner, Observer {
            stepAdapter.data = it
            stepAdapter.notifyDataSetChanged()

        })

        return view
    }

    private fun setupRecyclerView() = binding.mRecycler.apply {
        stepAdapter = BlockStepAdapter()
        adapter = stepAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager(requireContext()).orientation
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
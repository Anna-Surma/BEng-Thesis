package com.example.inzynierka_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.ErrorDialog
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentModeBinding
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeFragment : Fragment() {

    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!

    private val errorDialog = ErrorDialog()

    private lateinit var viewModel: GripperViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentModeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

        binding.tbActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.activeControl()
            } else {
                viewModel.deactivateControl()
            }
        }

        binding.btnTest.setOnClickListener {
            saveErrorToDb()
        }

        binding.btnTest2.setOnClickListener {
            errorDialog.createDialog(context, R.string.gripper_sensor_error_name, R.string.gripper_sensor_error_desc, R.drawable.error_red)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveErrorToDb() {
        val gripperError = com.example.inzynierka_app.db.GripperError(
            "00:26/08.11.2022",
            "RIGHT SENSOR",
            "No signal from the right sensor"
        )
        viewModel.insertRun(gripperError)
    }
}
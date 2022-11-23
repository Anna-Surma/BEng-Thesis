package com.example.inzynierka_app.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentModeBinding
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ModeFragment : Fragment() {

    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!

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
            val builder = AlertDialog.Builder(context)
            with(builder) {
                setTitle(R.string.top_right_sensor_error_name)
                setMessage(R.string.top_right_sensor_error_desc)
                setIcon(R.drawable.error_red)
                setPositiveButton("OK"){dialog: DialogInterface,_ -> dialog.cancel()}
                show()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveErrorToDb() {
        val current = LocalDateTime.now()
        val gripperError = com.example.inzynierka_app.db.GripperError(
            current.format(DateTimeFormatter.ofPattern("HH:mm:ss/dd.MM.yy")),
            "RIGHT SENSOR",
            "No signal from the right sensor"
        )
        viewModel.insertRun(gripperError)
    }
}
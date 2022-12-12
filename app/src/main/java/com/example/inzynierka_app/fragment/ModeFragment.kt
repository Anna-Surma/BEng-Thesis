package com.example.inzynierka_app.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.*
import com.example.inzynierka_app.databinding.FragmentModeBinding
import com.example.inzynierka_app.model.ParamsRead
import com.example.inzynierka_app.model.ParamsWrite
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ModeFragment : Fragment() {

    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!

    private var arrayErrorRequest = arrayListOf(
        ReadArrayRequestItem(1, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_error_HOR_left")),
        ReadArrayRequestItem(
            2,
            "2.0",
            "PlcProgram.Read",
            ParamsRead("\"Data\".mb_error_HOR_right")
        ),
        ReadArrayRequestItem(3, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_error_VTK_up")),
        ReadArrayRequestItem(4, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_error_VTK_down")),
        ReadArrayRequestItem(5, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_error_GRP_open")),
        ReadArrayRequestItem(6, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_catch_error"))
    )

    private var arrayStepsRequest = arrayListOf(
        ReadArrayRequestItem(1, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_1")),
        ReadArrayRequestItem(2, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_2")),
        ReadArrayRequestItem(3, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_3")),
        ReadArrayRequestItem(4, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_4")),
        ReadArrayRequestItem(5, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_5")),
        ReadArrayRequestItem(6, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_6")),
        ReadArrayRequestItem(7, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_7")),
        ReadArrayRequestItem(8, "2.0", "PlcProgram.Read", ParamsRead("\"Data\".mb_step_8"))
    )

    private lateinit var viewModel: GripperViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentModeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)
        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.arrayErrorResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                for (nr in it) {
                    if (nr.result) {
                        val errorType = when (nr.id) {
                            1 -> ErrorType.HOR_LEFT
                            2 -> ErrorType.HOR_RIGHT
                            3 -> ErrorType.VTK_TOP
                            4 -> ErrorType.VTK_DOWN
                            5 -> ErrorType.GRIPPER
                            6 -> ErrorType.PUT
                            else -> ErrorType.NETWORK
                        }
                        val builder = AlertDialog.Builder(context)
                        with(builder) {
                            setTitle(errorType.errorName)
                            setMessage(errorType.errorDesc)
                            setIcon(R.drawable.error_red)
                            setPositiveButton("OK") { dialog: DialogInterface, _ ->
                                dialog.cancel()
                                viewModel.writeData(ParamsWrite("\"Data\".mb_app_btn_error", true))
                                viewModel.readErrors(arrayErrorRequest)
                                viewModel.writeData(ParamsWrite("\"Data\".mb_app_btn_error", false))
                            }
                            show()
                        }
                        if (!builder.create().isShowing) {
                            saveErrorToDb(errorType.errorName, errorType.errorDesc)
                            viewModel.writeData(ParamsWrite("\"Data\".mb_app_btn_error", false))
                        }
                        viewModel.stopReadErrors()
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

    private fun saveErrorToDb(name: Int, description: Int) {
        val current = LocalDateTime.now()
        val gripperError = com.example.inzynierka_app.db.GripperError(
            current.format(DateTimeFormatter.ofPattern("HH:mm:ss/dd.MM.yy")),
            resources.getString(name),
            resources.getString(description)
        )
        viewModel.insertError(gripperError)
    }
}
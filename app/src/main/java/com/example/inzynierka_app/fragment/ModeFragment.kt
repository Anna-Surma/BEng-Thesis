package com.example.inzynierka_app.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.*
import com.example.inzynierka_app.databinding.FragmentModeBinding
import com.example.inzynierka_app.model.ParamsWrite
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
        binding.gripperViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                val builder = AlertDialog.Builder(context)
                with(builder) {
                    setTitle(it.errorName)
                    setMessage(it.errorDesc)
                    setIcon(R.drawable.error_red)
                    setPositiveButton("OK") { dialog: DialogInterface, _ ->
                        dialog.cancel()
                  //      viewModel.writeData2(ParamsWrite("\"DB10\".mb_app_btn_error", true))
                  //      viewModel.readErrors(RequestArrays.ERRORS.array)
                  //      viewModel.writeData2(ParamsWrite("\"DB10\".mb_app_btn_error", false))
                        viewModel.quitErrors()
                        Log.i("errorResponse", it.errorName.toString())
                    }
                    show()
                }
                if (!builder.create().isShowing) {
                    saveErrorToDb(it.errorName, it.errorDesc)
                    viewModel.writeData2(ParamsWrite("\"DB100\".mb_app_btn_error", false))
                }
                viewModel.stopReadErrors()
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
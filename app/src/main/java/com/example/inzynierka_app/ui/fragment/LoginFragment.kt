package com.example.inzynierka_app.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inzynierka_app.other.ErrorType
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentLoginBinding
import com.example.inzynierka_app.model.ParamsLogin
import com.example.inzynierka_app.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[(LoginViewModel::class.java)]

        binding.btnLogin.setOnClickListener {
            viewModel.loginUser(ParamsLogin(binding.loginTextInputText.text.toString(), binding.passwordTextInputText.text.toString()))
        }

        viewModel.logInEvent.observe(viewLifecycleOwner) {
            if (it.canLogIn && it.token != null) {
                findNavController().navigate(R.id.action_loginFragment_to_autoFragment)
            }
        }

        viewModel.loginErrorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.loginTextInputLayout.error = resources.getString(it)
                binding.passwordTextInputLayout.error = resources.getString(it)
            } else {
                binding.loginTextInputLayout.error = null
                binding.passwordTextInputLayout.error = null
            }
        }

        viewModel.networkErrorMessageBox.observe(viewLifecycleOwner) {
            if (it != null) {
                val builder = AlertDialog.Builder(context)
                with(builder) {
                    setTitle(ErrorType.NETWORK.errorName)
                    setMessage(it)
                    builder.setIcon(R.drawable.error_icon_desc)
                    setPositiveButton("OK") { dialog: DialogInterface, _ -> dialog.cancel() }
                    show()
                }
            } else {
                binding.loginTextInputLayout.error = null
                binding.passwordTextInputLayout.error = null
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

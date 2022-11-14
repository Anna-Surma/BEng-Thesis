package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inzynierka_app.ErrorDialog
import com.example.inzynierka_app.ErrorType
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentLoginBinding
import com.example.inzynierka_app.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val errorDialog = ErrorDialog()

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            viewModel.onSignInButtonClicked()
        }

        viewModel.logInEvent.observe(viewLifecycleOwner) {
            if (it.canLogIn && it.token != null) {
                findNavController().navigate(R.id.action_loginFragment_to_autoFragment)
            }
        }

        viewModel.loginErrorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.emailTextInputLayout.error = resources.getString(it)
                binding.passwordTextInputLayout.error = resources.getString(it)
            } else {
                binding.emailTextInputLayout.error = null
                binding.passwordTextInputLayout.error = null
            }
        }

        viewModel.networkErrorMessageBox.observe(viewLifecycleOwner) {
            if (it != null) {
                errorDialog.createDialog(context, ErrorType.NETWORK.errorName, it, R.drawable.error_icon_desc)
            }
            else {
                binding.emailTextInputLayout.error = null
                binding.passwordEditText.error = null
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.inzynierka_app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka_app.GripperActivity
import com.example.inzynierka_app.api.ApiClient
import com.example.inzynierka_app.api.SessionManager
import com.example.inzynierka_app.databinding.FragmentLoginBinding
import com.example.inzynierka_app.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.initializeSessionManager(requireActivity())
        ApiClient().initialize(requireContext())
        sessionManager = SessionManager(requireActivity())

        binding.loginButton.setOnClickListener {
            viewModel.onSignInButtonClicked()
        }

        viewModel.logInEvent.observe(viewLifecycleOwner) {
            if (it.canLogIn && it.token != null) {
                sessionManager.saveAuthToken(it.token!!)
                val intent = Intent(activity, GripperActivity::class.java)
                startActivity(intent)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

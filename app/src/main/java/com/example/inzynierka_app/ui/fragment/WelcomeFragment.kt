package com.example.inzynierka_app.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private  var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnStartButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        binding.ivFbIcon.setOnClickListener {
            gotUrl("https://www.facebook.com/blumpolska")
        }

        binding.ivInstIcon.setOnClickListener {
            gotUrl("https://www.instagram.com/blumpolska/")
        }

        binding.ivPintIcon.setOnClickListener {
            gotUrl("https://www.pinterest.at/BlumFittings/")
        }

        binding.ivYtIcon.setOnClickListener {
            gotUrl("https://www.youtube.com/BlumPolskaProdukty")
        }
        return view
    }

    private fun gotUrl(url: String) {
        val uri: Uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
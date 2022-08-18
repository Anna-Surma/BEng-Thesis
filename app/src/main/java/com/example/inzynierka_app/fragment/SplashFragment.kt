package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.inzynierka_app.R
import com.example.inzynierka_app.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private  var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        //TODO change handler on Coroutine
        handler.postDelayed({
            view.findNavController()
                .navigate(R.id.action_splashFragment_to_welcomeFragment)
        }, 3000)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
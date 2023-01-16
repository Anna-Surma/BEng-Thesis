package com.example.inzynierka_app

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inzynierka_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var errorActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavView.setupWithNavController(navController)

        binding.ivErrorIcon.setOnClickListener {
            if (!errorActive) {
                binding.tvMode.visibility = View.GONE
                binding.fcvMode.visibility = View.GONE
                binding.navHostFragment.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE
                binding.fcvError.visibility = View.VISIBLE
                errorActive = true
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.autoFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.auto_mode)
                    binding.ivErrorIcon.visibility = View.VISIBLE
                    binding.fcvError.visibility = View.GONE
                }
                R.id.manualFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.manual_mode)
                    binding.ivErrorIcon.visibility = View.VISIBLE
                    binding.fcvError.visibility = View.GONE
                }
                R.id.blockFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.block_mode)
                    binding.ivErrorIcon.visibility = View.VISIBLE
                    binding.fcvError.visibility = View.GONE
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                    binding.fcvMode.visibility = View.GONE
                    binding.tvMode.visibility = View.GONE
                    binding.ivErrorIcon.visibility = View.GONE
                    binding.fcvError.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        if (errorActive) {
            binding.tvMode.visibility = View.VISIBLE
            binding.fcvMode.visibility = View.VISIBLE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.bottomNav.visibility = View.VISIBLE
            binding.fcvError.visibility = View.GONE
            errorActive = false
        }
    }
}
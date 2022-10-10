package com.example.inzynierka_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inzynierka_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.autoFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.auto_mode)
                }
                R.id.manualFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.manual_mode)
                }
                R.id.blockFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fcvMode.visibility = View.VISIBLE
                    binding.tvMode.visibility = View.VISIBLE
                    binding.tvMode.setText(R.string.block_mode)
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                    binding.fcvMode.visibility = View.GONE
                    binding.tvMode.visibility = View.GONE
                }
            }
        }
    }
}
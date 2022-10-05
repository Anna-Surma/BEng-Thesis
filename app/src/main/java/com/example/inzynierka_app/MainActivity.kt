package com.example.inzynierka_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inzynierka_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.autoFragment, R.id.manualFragment, R.id.blockFragment ->
                    binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }
        }
    }
}
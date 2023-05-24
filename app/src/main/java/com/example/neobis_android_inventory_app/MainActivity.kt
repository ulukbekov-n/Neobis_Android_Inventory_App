package com.example.neobis_android_inventory_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.neobis_android_inventory_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNav
        navController = findNavController(R.id.fragmentNavController)
        bottomNavigationView.setupWithNavController(navController)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragmentNavController))


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentNavController)
        return navController.navigateUp()||super.onSupportNavigateUp()
    }




}



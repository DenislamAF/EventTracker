package com.example.eventracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.eventracker.R
import com.example.eventracker.databinding.ActivityMainBinding
import com.example.eventracker.databinding.BottomNavigationFragmentBinding
import com.example.eventracker.presentation.fragments.AppInfoFragment
import com.example.eventracker.presentation.fragments.BottomNavigationFragment
import com.example.eventracker.presentation.fragments.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction()
            .replace(binding?.mainContainer!!.id, LoginFragment())
            .replace(binding?.bottomContainer!!.id, AppInfoFragment())
            .commit()

    }

}
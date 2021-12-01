package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eventracker.R
import com.example.eventracker.databinding.BottomNavigationFragmentBinding

class BottomNavigationFragment: Fragment() {
    private var bottomNavigationFragmentBinding: BottomNavigationFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationFragmentBinding =  BottomNavigationFragmentBinding.inflate(inflater, container, false)

        return bottomNavigationFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationFragmentBinding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.addToBackStack(null)
                        ?.replace(R.id.main_container, ProfileFragment())
                        ?.replace(R.id.bottom_container, BottomNavigationFragment())
                        ?.commitAllowingStateLoss()
                    true
                }
                R.id.navigation_invitations -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.addToBackStack(null)
                        ?.replace(R.id.main_container, SearchFragment())
                        ?.replace(R.id.bottom_container, BottomNavigationFragment())
                        ?.commitAllowingStateLoss()
                    true
                }
                R.id.navigation_my_events -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.addToBackStack(null)
                        ?.replace(R.id.main_container, MainEventFragment())
                        ?.replace(R.id.bottom_container, BottomNavigationFragment())
                        ?.commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }
}
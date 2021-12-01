package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.AppInfoFragmentBinding
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.databinding.ProfileFragmentBinding

class ProfileFragment: Fragment() {
    private var profileFragmentBinding: ProfileFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false)
        return profileFragmentBinding?.root
    }
}
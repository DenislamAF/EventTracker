package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.ProfileFragmentBinding
import com.example.eventracker.databinding.SearchFragmentBinding

class SearchFragment: Fragment() {
    private var searchFragmentBinding: SearchFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return searchFragmentBinding?.root
    }
}
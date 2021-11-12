package com.example.eventracker.presentation.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventracker.R
import com.example.eventracker.data.GeneralRepository
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.presentation.adapters.EventListAdapter
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.RegistrationFragmentViewModel

class MainEventFragment: Fragment() {
    private lateinit var eventListAdapter: EventListAdapter
    private var mainEventFragmentBinding: MainEventFragmentBinding? = null
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainEventFragmentBinding = MainEventFragmentBinding.inflate(inflater, container, false)
        return mainEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        Toast.makeText(this.context, mainFragmentViewModel.getUserLiveData()?.value?.email.toString(), Toast.LENGTH_SHORT).show()
        setRecyclerView()
        mainFragmentViewModel.getUserLiveDatabase()?.observe(viewLifecycleOwner){
            eventListAdapter.list = it.listOfEvents
        }
        mainEventFragmentBinding?.buttonAddEvent?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.main_container, AddEventFragment())
                ?.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainFragmentViewModel.logOut()
    }

    private fun setRecyclerView(){
        val recyclerView = mainEventFragmentBinding?.eventList
        recyclerView?.layoutManager = LinearLayoutManager(context)
        eventListAdapter = EventListAdapter()
        //eventListAdapter.list = mainFragmentViewModel.user!!.listOfEvents
        recyclerView?.adapter = eventListAdapter

    }
}
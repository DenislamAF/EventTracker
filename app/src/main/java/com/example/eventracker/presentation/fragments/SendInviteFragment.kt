package com.example.eventracker.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.databinding.InviteToEventFragmentBinding
import com.example.eventracker.domain.models.Event
import com.example.eventracker.presentation.adapters.SearchedUsersListAdapter
import com.example.eventracker.presentation.viewmodels.SendInviteFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import com.google.android.gms.maps.model.LatLng
import java.lang.RuntimeException

class SendInviteFragment: Fragment() {
    private lateinit var searchedUsersListAdapter: SearchedUsersListAdapter
    private lateinit var sendInviteFragmentViewModel: SendInviteFragmentViewModel
    private var inviteToEventFragmentBinding: InviteToEventFragmentBinding? = null

    private var eventKey: String = Event.UNDEFINED_KEY
    private var mode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inviteToEventFragmentBinding = InviteToEventFragmentBinding.inflate(inflater, container, false)
        return inviteToEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendInviteFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[SendInviteFragmentViewModel::class.java]
        getArgs()
        sendInviteFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            setInfo()
        }

        inviteToEventFragmentBinding?.inputName?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                Log.d("TEXT in EDIT", s.toString())
                val users = sendInviteFragmentViewModel.getUsersBySearchText(s.toString())
                Log.d("Users:", users.toString())
                searchedUsersListAdapter.list = users
            }
        })

        setRecyclerView()
        setupClickListener()
    }

    private fun setupClickListener() {
        searchedUsersListAdapter.onShopItemClickListener = { selectedIt ->
            searchedUsersListAdapter.list =
                searchedUsersListAdapter.list.filter{ it.userID != selectedIt.userID }
            sendInviteFragmentViewModel.sendInvite(selectedIt.userID, sendInviteFragmentViewModel.event.value!!)
            Toast.makeText(activity, "Приглашение отправлено!", Toast.LENGTH_LONG).show()
        }
    }

    private fun setInfo(){
        sendInviteFragmentViewModel.getEventByKey(mode, eventKey)
        val event = sendInviteFragmentViewModel.event.value
        inviteToEventFragmentBinding?.apply {
            detailedDateEventTV.text = event?.date
            detailedTimeEventTV.text = event?.time
        }
    }

    private fun setRecyclerView(){
        val recyclerView = inviteToEventFragmentBinding?.namesList
        recyclerView?.layoutManager = LinearLayoutManager(context)
        searchedUsersListAdapter = SearchedUsersListAdapter()
        recyclerView?.adapter = searchedUsersListAdapter
    }

    private fun getArgs(){
        eventKey = requireArguments().getString(EVENT_KEY) as String
        mode = requireArguments().getString(MODE_KEY) as String
    }
    companion object{
        fun newInstanceSendInviteFragment(mode: String, eventKey: String): SendInviteFragment{
            return SendInviteFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_KEY, mode)
                    putString(EVENT_KEY, eventKey)
                }
            }
        }

        private const val MODE_KEY = "modeKey"
        private const val EVENT_KEY = "eventID"
    }
}
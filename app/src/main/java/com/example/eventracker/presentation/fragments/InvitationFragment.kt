package com.example.eventracker.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.databinding.InvitationFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.presentation.adapters.EventListAdapter
import com.example.eventracker.presentation.adapters.InvitationListAdapter
import com.example.eventracker.presentation.viewmodels.InvitationFragmentViewModel
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import java.lang.RuntimeException

class InvitationFragment: Fragment() {
    private lateinit var invitationListAdapter: InvitationListAdapter
    private lateinit var invitationFragmentViewModel: InvitationFragmentViewModel
    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener
    private var invitationFragmentBinding: InvitationFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentsInteractionsListener){
            onFragmentsInteractionsListener = context
        }else{
            throw RuntimeException("Activity must implement OnFragmentsInteractionsListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        invitationFragmentBinding = InvitationFragmentBinding.inflate(inflater, container, false)
        return invitationFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invitationFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[InvitationFragmentViewModel::class.java]
        setRecyclerView()
        setupClickListener()
        invitationFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            invitationListAdapter.list = it.listOfInvitations
        }
    }

    private fun setupClickListener() {
        invitationListAdapter.onShopItemClickListener = {
            onFragmentsInteractionsListener.onAddBackStack(
                "invite",
                DetailedEventFragment.newInstanceDetailedEventFragment(GeneralRepositoryImpl.GET_FROM_INVITE_LIST, it.key))
        }
    }

    private fun setRecyclerView(){
        val recyclerView = invitationFragmentBinding?.inviteList
        recyclerView?.layoutManager = LinearLayoutManager(context)
        invitationListAdapter = InvitationListAdapter()
        recyclerView?.adapter = invitationListAdapter
        setupSwipeListener(recyclerView as RecyclerView)

    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val myCallBack = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction){
                    ItemTouchHelper.LEFT -> {
                        val item = invitationListAdapter.list[viewHolder.adapterPosition]
                        invitationFragmentViewModel.addInviteToEventsUseCase(item)
                        invitationFragmentViewModel.deleteInvite(item)
                    }

                    ItemTouchHelper.RIGHT -> {
                        val item = invitationListAdapter.list[viewHolder.adapterPosition]
                        invitationFragmentViewModel.deleteInvite(item)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(myCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
package com.example.eventracker.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.databinding.NameBinding
import com.example.eventracker.domain.models.User

class SearchedUsersListAdapter: RecyclerView.Adapter<SearchedUsersListAdapter.NameItemHolder>() {

    var onShopItemClickListener: ((User) -> Unit)? = null

    //TODO CHANGE UPDATE
    var list = listOf<User>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameItemHolder {
        val eventView = NameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NameItemHolder(eventView)
    }

    override fun onBindViewHolder(holder: NameItemHolder, position: Int) {
        val user = list[position]
        holder.nameBinding.userNameTv.text = user.login
        holder.nameBinding.root.setOnClickListener {
            onShopItemClickListener?.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NameItemHolder(val nameBinding: NameBinding): RecyclerView.ViewHolder(nameBinding.root)
}
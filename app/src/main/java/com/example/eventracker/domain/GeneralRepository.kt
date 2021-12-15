package com.example.eventracker.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.models.User
import com.google.firebase.auth.FirebaseUser

interface GeneralRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(name: String, email: String, password: String)

    //TODO DELETE?
    fun getUserFromFirebase()

    suspend fun createEvent(event: Event)
    suspend fun deleteEvent(event: Event)

    suspend fun deleteInvite(event: Event)
    suspend fun addInviteToEvents(event: Event)
    suspend fun sendInviteToUser(uid: String, event: Event)

    fun logOut()
    fun getFirebaseUser(): LiveData<FirebaseUser>
    fun getUser(): LiveData<User>
    fun getFirebaseInfo(): LiveData<String>
    fun getEventByKey(mode: String, key: String): Event
    fun getUsersBySearchText(searchText: String): List<User>
}
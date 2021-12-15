package com.example.eventracker.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.models.User
import com.example.eventracker.domain.usecases.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO всё изменить
class SendInviteFragmentViewModel(private val sendInviteUseCase: SendInviteUseCase,
                                  private val searchUseCase: SearchUseCase,
                                  private val getUserDatabaseUseCase: GetUserDatabaseUseCase,
                                  private val getEventByKeyUseCase: GetEventByKeyUseCase): ViewModel() {

    private var userLiveDatabase: LiveData<User> = getUserDatabaseUseCase.getUser()

    var event = MutableLiveData<Event>()

    fun getEventByKey(mode: String, key: String){
        event.value = getEventByKeyUseCase.getEventByKey(mode,key)
    }

    fun sendInvite(uid: String, event: Event){
        viewModelScope.launch(Dispatchers.Main) {
            sendInviteUseCase.sendInvite(uid, event)
        }
    }

    fun getUsersBySearchText(text: String): ArrayList<User>{
        return searchUseCase.getUsersBySearchText(text)
    }

    fun getUserLiveDatabase(): LiveData<User>{
        return userLiveDatabase
    }
}
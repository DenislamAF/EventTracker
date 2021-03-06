package com.example.eventracker.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.eventracker.data.RetrofitClient
import com.example.eventracker.data.RetrofitServices
import com.example.eventracker.domain.LoginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import com.google.firebase.auth.FirebaseUser

import androidx.lifecycle.MutableLiveData
import com.example.eventracker.data.GeneralRepository


class LoginFragmentViewModel(application: Application): AndroidViewModel(application) {
    private var generalRepository: GeneralRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null

    init {
        generalRepository = GeneralRepository(application)
        userLiveData = generalRepository?.getUserLiveData()
    }

    fun login(email: String, password: String){
            generalRepository?.login(email, password)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun checkInput(email: String, password: String): Boolean{
        return email.length > 1 && password.length > 1
    }
    /*
    fun authorization (login: String, password: String): Boolean{
        var flag = false
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitClient.retrofitServices.auth(LoginBody(login,password))
            } catch (ex: IOException){
                return@launch
            }
            if (response.code() == RetrofitServices.CORRECT_AUTO) {
                flag = true
                return@launch
            }
        }
        return flag
    }
     */
}


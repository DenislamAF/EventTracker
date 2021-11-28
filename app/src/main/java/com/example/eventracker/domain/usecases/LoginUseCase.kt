package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository

class LoginUseCase(private val generalRepository: GeneralRepository) {

    suspend fun login(email: String, password: String){
        generalRepository.login(email,password)
    }
}
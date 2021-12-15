package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.User

class SearchUseCase(private val generalRepository: GeneralRepository) {
    fun getUsersBySearchText(text: String): ArrayList<User> {
        return generalRepository.getUsersBySearchText(text)
    }
}
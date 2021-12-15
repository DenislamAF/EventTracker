package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.Event

class SendInviteUseCase(private val generalRepository: GeneralRepository) {
    suspend fun sendInvite(uid: String, event: Event){
        generalRepository.sendInviteToUser(uid, event)
    }
}
package com.example.eventracker.domain.models

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlin.collections.ArrayList

data class Event(
    val key: String = UNDEFINED_KEY,
    val creator: String = "",
    val date: String = "",
    val name: String = "",
    val description: String = "",
    val members: List<User> = arrayListOf(),
    val eventPosition: LatLng = LatLng(-33.852, 151.211),
    val time: String = ""
){
    companion object{
        const val UNDEFINED_KEY = "-1"
    }
}

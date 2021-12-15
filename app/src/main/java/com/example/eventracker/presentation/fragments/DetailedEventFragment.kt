package com.example.eventracker.presentation.fragments

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.databinding.ActivityMainBinding.inflate
import com.example.eventracker.databinding.DetailedEventFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.domain.models.Event
import com.example.eventracker.presentation.fragments.SendInviteFragment.Companion.newInstanceSendInviteFragment
import com.example.eventracker.presentation.viewmodels.DetailedEventFragmentViewModel
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.RuntimeException
import java.util.*

class DetailedEventFragment: Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var detailedEventFragmentViewModel: DetailedEventFragmentViewModel
    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener
    private var detailedEventFragmentBinding: DetailedEventFragmentBinding? = null
    private var eventKey: String = Event.UNDEFINED_KEY

    //TODO исправить это недоразумение
    private var eL = MutableLiveData<LatLng>()
    private var mode = ""

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
        detailedEventFragmentBinding = DetailedEventFragmentBinding.inflate(inflater, container, false)
        return detailedEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedEventFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[DetailedEventFragmentViewModel::class.java]
        initMap()
        getArgs()

        detailedEventFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            setInfo()
        }

        detailedEventFragmentBinding?.buttonInvite?.setOnClickListener {
            onFragmentsInteractionsListener.onAddBackStack(
                "sendInvite",
                newInstanceSendInviteFragment(mode, eventKey))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        eL.observe(viewLifecycleOwner) {
            mMap.addMarker(MarkerOptions().position(it).title("Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 17f))
            getPositionAddress(it)
        }
    }

    private fun setInfo(){
        detailedEventFragmentViewModel.getEventByKey(mode, eventKey)
        val event = detailedEventFragmentViewModel.event.value
            detailedEventFragmentBinding?.apply {
            detailedNameEventTV.text = event?.name
            detailedDateEventTV.text = event?.date
            detailedTimeEventTV.text = event?.time
            detailedDescriptionEventTV.text = event?.description
                eL.value = event!!.eventPosition
        }
    }

    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.detailed_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getArgs(){
        eventKey = requireArguments().getString(EVENT_KEY) as String
        mode = requireArguments().getString(MODE_KEY) as String
    }

    //TODO сделать асинхронным
    private fun getPositionAddress(latLng: LatLng){
        val addresses: MutableList<Address>
        val geocoder = Geocoder(context, Locale.getDefault())
        addresses =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        detailedEventFragmentBinding?.locationAddress?.text =
            addresses[0].getAddressLine(0) ?: "Unknown location"
    }

    companion object{
        fun newInstanceDetailedEventFragment(mode: String, eventKey: String): DetailedEventFragment{
            return DetailedEventFragment().apply {
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
package com.example.eventracker.presentation.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.databinding.AddEventFragmentBinding
import com.example.eventracker.presentation.viewmodels.AddEventFragmentViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragment: Fragment(), DatePickerDialog.OnDateSetListener {
    private var addEventFragmentBinding: AddEventFragmentBinding? = null
    private lateinit var addEventFragmentViewModel: AddEventFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEventFragmentBinding = AddEventFragmentBinding.inflate(inflater, container, false)
        return addEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEventFragmentViewModel = ViewModelProvider(this)[AddEventFragmentViewModel::class.java]

        //TODO CHECK INPUT
        addEventFragmentBinding?.saveButton?.setOnClickListener {
            val eventName: String = addEventFragmentBinding?.eventNameEt?.text.toString()
            val eventDescription: String = addEventFragmentBinding?.eventDescriptionEt?.text.toString()
            val date: String = addEventFragmentViewModel.checkDate(addEventFragmentBinding?.eventDateEt?.text.toString())
            if (addEventFragmentViewModel.checkInput(eventName, eventDescription)) {
                addEventFragmentViewModel.createNewEvent(
                    eventName, eventDescription, date)
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.addToBackStack("login")
                    ?.replace(R.id.main_container, MainEventFragment())
                    ?.commit()
            }else Toast.makeText(this.context, "Something is wrong", Toast.LENGTH_SHORT).show()

        }

        addEventFragmentBinding?.eventDateEt?.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this.context as Context,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    //TODO DO NORMAL DATE, NOT STRING
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val gregorianCalendar = GregorianCalendar(year, month, dayOfMonth)
        addEventFragmentBinding?.eventDateEt?.text = dateFormat.format(gregorianCalendar.time)
    }
}
package com.studyup.classes.NewTeam.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.util.Pair
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.Event
import com.studyup.databinding.FragmentEventsBinding
import com.studyup.utils.State
import java.text.SimpleDateFormat
import java.util.*

class Events() : Fragment() {
    private lateinit var _binding: FragmentEventsBinding
    private var fragmentRecycler: EventsFragmentList? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentRecycler = EventsFragmentList()
        val fragment = this.fragmentRecycler
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commitNow()
        }

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        //val contextMenuTextView = _binding.root.findViewById<TextInputLayout>(R.id.filledTextField)
        _binding.ArrowAdd.setOnClickListener { _ ->
            this.context?.let {
                buildDialog(it)
            }
            //true
        }
        //insertEventsIfTeamAlreadyExists()
        return _binding.root

        //return inflater.inflate(R.layout.fragment_events, container, false)
    }

    private fun buildDialog(it: Context) {
        val viewDialog =
            LayoutInflater.from(this.context).inflate(R.layout.fragment_new_event_dialog, null)
        val dialogNewTag = MaterialAlertDialogBuilder(it)
            .setTitle("Nuevo evento")
            .setView(viewDialog)
            .show()

        setDialogButtonListeners(viewDialog, dialogNewTag)
    }

    private fun setDialogButtonListeners(
        viewDialog: View,
        dialogNewTag: AlertDialog
    ) {
        viewDialog.findViewById<Button>(R.id.dateRangeButton).setOnClickListener {
            setDateRangeButton(viewDialog)
        }
        viewDialog.findViewById<Button>(R.id.button_save).setOnClickListener {
            var text_title = viewDialog.findViewById<TextInputLayout>(R.id.title).editText?.text.toString()
            var text_start_date = viewDialog.findViewById<TextView>(R.id.start_date).text.toString()
            var text_end_date = viewDialog.findViewById<TextView>(R.id.end_date).text.toString()
            if (text_title!="" && text_start_date !="" && text_end_date !=""){
                insertNewEvent(
                    viewDialog,
                    text_title,
                    text_start_date,
                    text_end_date,
                    dialogNewTag
                )
            }else{
                showMissingFieldAlert(
                    viewDialog,
                    text_title,
                    text_start_date,
                    text_end_date
                )
            }
        }
        viewDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialogNewTag.cancel()
        }
    }

    private fun showMissingFieldAlert(
        viewDialog: View,
        text_title: String,
        text_start_date: String,
        text_end_date: String
    ) {
        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
        viewDialog.findViewById<TextInputLayout>(R.id.start_date).error = null
        viewDialog.findViewById<TextInputLayout>(R.id.end_date).error = null
        if (text_title == "")
            viewDialog.findViewById<TextInputLayout>(R.id.title).error = "Completar el campo"
        if (text_start_date == "")
            viewDialog.findViewById<TextView>(R.id.start_date).error = "Completar el campo"
        if (text_end_date == "")
            viewDialog.findViewById<TextView>(R.id.end_date).error = "Completar el campo"
    }

    private fun insertNewEvent(
        viewDialog: View,
        text_title: String,
        text_start_date: String,
        text_end_date: String,
        dialogNewTag: AlertDialog
    ) {
        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
        viewDialog.findViewById<TextView>(R.id.start_date).error = null
        viewDialog.findViewById<TextView>(R.id.end_date).error = null
        State.newTeam.addEvent(Event(text_title, text_start_date, text_end_date))
        this.fragmentRecycler!!.notify_update()
        dialogNewTag.cancel()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setDateRangeButton(viewDialog: View) {
        val builder: MaterialDatePicker.Builder<Pair<Long, Long>> =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Ingrese la fechas fechas de inicio y fin")
        val constraintsBuilder = CalendarConstraints.Builder()
        builder.setCalendarConstraints(constraintsBuilder.build())
        val picker: MaterialDatePicker<*> = builder.build()

        picker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val startDateTextView = viewDialog.findViewById<TextView>(R.id.start_date)
            val endDateTextView = viewDialog.findViewById<TextView>(R.id.end_date)
            val pickedDateRange: Pair<Long, Long> = picker.selection as Pair<Long, Long>
            val (startDate, endDate) = pickedDateRange
            startDateTextView.text = formatter.format(Date(startDate))
            endDateTextView.text = formatter.format(Date(endDate))
        }

        picker.show(requireActivity().supportFragmentManager, picker.toString())
    }
}
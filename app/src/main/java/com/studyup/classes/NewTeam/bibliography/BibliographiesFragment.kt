package com.studyup.classes.NewTeam.bibliography

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Bibliography
import com.studyup.databinding.FragmentBibliographiesBinding
import com.studyup.utils.State

class BibliographiesFragment(private var bibliographies: MutableList<Bibliography>? = null) :
    Fragment() {
    private lateinit var _binding: FragmentBibliographiesBinding
    private var fragmentRecycler: BibliographiesFragmentList? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentRecycler = BibliographiesFragmentList()
        val fragment = this.fragmentRecycler
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commitNow()
        }

        _binding = FragmentBibliographiesBinding.inflate(inflater, container, false)
        _binding.ArrowAdd.setOnClickListener { _ ->
            this.context?.let {
                buildDialog(it)
            }
        }

        insertBibliographiesIfTeamAlreadyExists()
        return _binding.root
    }

    private fun insertBibliographiesIfTeamAlreadyExists() {
        if (!bibliographies.isNullOrEmpty()) {
            bibliographies!!.forEach { bibliography ->
                APIService.insertBibliographies(
                    bibliography.title,
                    bibliography.description
                )
            }
            this.fragmentRecycler!!.notify_update()
        }
    }

    private fun buildDialog(it: Context) {
        val viewDialog =
            LayoutInflater.from(this.context)
                .inflate(R.layout.fragment_new_team_bibliographies_dialog, null)
        val dialogNewTag = MaterialAlertDialogBuilder(it)
            .setTitle("Nueva bibliografia")
            .setView(viewDialog)
            .show()

        setDialogButtonListeners(viewDialog, dialogNewTag)
    }

    private fun setDialogButtonListeners(
        viewDialog: View,
        dialogNewTag: AlertDialog
    ) {
        viewDialog.findViewById<Button>(R.id.button_save).setOnClickListener {
            val title = viewDialog.findViewById<TextInputLayout>(R.id.title).editText?.text.toString()
            val description = viewDialog.findViewById<TextInputLayout>(R.id.description).editText?.text.toString()
            if (title != "" && description != "") {
                insertNewBibliography(viewDialog, title, description, dialogNewTag)
            } else {
                showMissingFieldAlert(viewDialog, title, description)
            }
        }
        viewDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialogNewTag.cancel()
        }
    }

    private fun insertNewBibliography(
        viewDialog: View,
        title: String,
        description: String,
        dialogNewTag: AlertDialog
    ) {
        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
        viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
        State.newTeam.addBibliography(Bibliography(title, description))
        APIService.insertBibliographies(title, description)
        this.fragmentRecycler!!.notify_update()
        dialogNewTag.cancel()
    }

    private fun showMissingFieldAlert(
        viewDialog: View,
        title: String,
        description: String
    ) {
        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
        viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
        if (title == "")
            viewDialog.findViewById<TextInputLayout>(R.id.title).error = "Completar el campo"
        if (description == "")
            viewDialog.findViewById<TextView>(R.id.description).error = "Completar el campo"
    }

}
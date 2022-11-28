package com.studyup.classes.NewTeam.tags

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.Activity
import com.studyup.api.Member
import com.studyup.api.Tag
import com.studyup.classes.NewTeam.members.MembersFragmentList
import com.studyup.databinding.FragmentNewTeamTagsBinding
import com.studyup.utils.State

class Tags: Fragment() {
    private lateinit var _binding: FragmentNewTeamTagsBinding
    private var fragmentRecicler: TagsFragmentList? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentRecicler = TagsFragmentList()
        val fragment = this.fragmentRecicler
        if (fragment != null) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commitNow()
        }

        _binding = FragmentNewTeamTagsBinding.inflate(inflater, container, false)
        _binding.ArrowAdd.setOnClickListener { _ ->
            this.context?.let {
                val viewDialog = LayoutInflater.from(this.context).inflate(R.layout.fragment_new_team_tags_dialog,null)
                val dialogNewTag = MaterialAlertDialogBuilder(it)
                    .setTitle("Nueva Tag")
                    .setView(viewDialog)
                    .show()
                viewDialog.findViewById<Button>(R.id.button_save).setOnClickListener{
                    var text_title = viewDialog.findViewById<TextInputLayout>(R.id.title).editText?.text.toString()
                    var text_description = viewDialog.findViewById<TextInputLayout>(R.id.description).editText?.text.toString()
                    if (text_title!="" && text_description !=""){
                        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
                        viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
                        addTag(text_title, text_description)
                        this.fragmentRecicler!!.notify_update()
                        dialogNewTag.cancel()
                    }else{
                        viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
                        viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
                        if (text_title=="")
                            viewDialog.findViewById<TextInputLayout>(R.id.title).error = "Completar el campo"
                        if (text_description=="")
                            viewDialog.findViewById<TextInputLayout>(R.id.description).error = "Completar el campo"
                    }
                }
                viewDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener{
                    dialogNewTag.cancel()
                }
            }


        }
        return _binding.root

    }

    private fun addTag(text_title: String, text_description: String) {
        State.newTeam.addTag(Tag(0, text_title, text_description, mutableListOf<Activity>()))
    }
}
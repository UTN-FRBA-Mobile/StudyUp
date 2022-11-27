package com.studyup.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.studyup.R
import com.studyup.api.Team
import com.studyup.databinding.FragmentNewTeamBinding
import com.studyup.utils.State
import com.studyup.utils.serializeToMap
import java.util.Collections.max

class NewTeam : Fragment() {

    private var _binding: FragmentNewTeamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewTeamBinding.inflate(inflater, container, false)
        toolbarMenuSetup()
        setButtonListeners()
        return binding.root

    }

    private fun setButtonListeners() {

        _binding!!.members.setOnClickListener {
            findNavController().navigate(R.id.action_newTeamFragment_to_MembersMain)
        }
        _binding!!.tags.setOnClickListener {
            findNavController().navigate(R.id.action_newTeamFragment_to_tags)
        }

        _binding!!.events.setOnClickListener {
            findNavController().navigate(R.id.action_newTeamFragment_to_eventsFragment)
        }

        _binding!!.bibliographies.setOnClickListener {
            findNavController().navigate(R.id.action_newTeamFragment_to_bibliography)
        }

        _binding!!.ArrowRight.setOnClickListener {
            if (_binding!!.title.editText?.text.toString()==""){
                _binding!!.title.error = "Completar el campo"
            }
            if (_binding!!.description.editText?.text.toString()==""){
                _binding!!.description.error = "Completar el campo"
            }
            if (_binding!!.title.editText?.text.toString()!="" && _binding!!.description.editText?.text.toString()!="") {
                FirebaseApp.initializeApp(this.requireContext())
                val database = Firebase.database
                database.getReference("team").get().addOnSuccessListener { it ->
                    var values = it.value as ArrayList<HashMap<String, String>>
                    var index = values.size
                    database.getReference("user").child("0/team").child(index.toString())
                        .setValue(index)
                    var body = getMappedTeam()
                    database.getReference("team").child(index.toString()).setValue(body)
                    findNavController().navigate(R.id.action_newTeamFragment_to_DashboardFragment)
                }
            }
        }
    }

    private fun getMappedTeam(): Map<String, Any> {
        var detailBody = State.newTeam.serializeToMap()
        var body = mapOf(
            "title" to _binding!!.title.editText?.text.toString(),
            "description" to _binding!!.description.editText?.text.toString(),
            "details" to detailBody
        )
        return body
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.action_search)
                menu.removeItem(R.id.action_add)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().navigate(R.id.action_newTeamFragment_to_DashboardFragment)
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
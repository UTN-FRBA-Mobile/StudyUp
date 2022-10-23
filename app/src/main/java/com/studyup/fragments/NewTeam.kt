package com.studyup.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.studyup.R
import com.studyup.databinding.FragmentNewTeamBinding

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

        _binding!!.events.setOnClickListener {
            findNavController().navigate(R.id.action_newTeamFragment_to_teamDetail)
        }
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.action_search)
                menu.removeItem(R.id.action_add)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId === android.R.id.home) {
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
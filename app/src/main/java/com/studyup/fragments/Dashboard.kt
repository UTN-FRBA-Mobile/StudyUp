package com.studyup.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.studyup.R
import com.studyup.classes.team.CardAdapter
import com.studyup.classes.team.Team
import com.studyup.classes.team.teams
import com.studyup.databinding.FragmentDashboardBinding

class Dashboard : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        toolbarMenuSetup()
        populateList()
        bindTeamsRecyclerVIew()
        return binding.root
    }

    private fun bindTeamsRecyclerVIew() {
        binding.teams.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CardAdapter(teams)
        }
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId === R.id.action_add) {
                    findNavController().navigate(R.id.action_DashboardFragment_to_newTeamFragment)
                }
                if (menuItem.itemId === android.R.id.home) {
                    findNavController().popBackStack()
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val menuHost: MenuHost = requireActivity()
        menuHost.invalidateMenu()
        _binding = null
    }

    private fun populateList(){
        val team1 = Team(
            R.drawable.placeholder,
            "Team 1",
            "Description"
        )
        teams.add(team1)

        val team2 = Team(
            R.drawable.placeholder,
            "Team 2",
            "Description"
        )
        teams.add(team2)

        val team3 = Team(
            R.drawable.placeholder,
            "Team 3",
            "Description"
        )
        teams.add(team3)

        val team4 = Team(
            R.drawable.placeholder,
            "Team 4",
            "Description"
        )
        teams.add(team4)

        val team5 = Team(
            R.drawable.placeholder,
            "Team 5",
            "Description"
        )
        teams.add(team5)

        val team6 = Team(
            R.drawable.placeholder,
            "Team 6",
            "Description"
        )
        teams.add(team6)

        val team7 = Team(
            R.drawable.placeholder,
            "Team 7",
            "Description"
        )
        teams.add(team7)

        val team8 = Team(
            R.drawable.placeholder,
            "Team 8",
            "Description"
        )
        teams.add(team8)

        val team9 = Team(
            R.drawable.placeholder,
            "Team 9",
            "Description"
        )
        teams.add(team9)
    }
}
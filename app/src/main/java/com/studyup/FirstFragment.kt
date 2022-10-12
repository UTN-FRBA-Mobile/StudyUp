package com.studyup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.studyup.classes.team.CardAdapter
import com.studyup.classes.team.Team
import com.studyup.classes.team.teams
import com.studyup.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        populateList()

        binding.teams.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CardAdapter(teams)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
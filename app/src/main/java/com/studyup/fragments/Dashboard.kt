package com.studyup.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.studyup.MainActivity
import com.studyup.R
import com.studyup.classes.team.CardAdapter
import com.studyup.classes.team.Team
import com.studyup.classes.team.filteredTeams
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
        //populateList()
        //bindTeamsRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inSession()
    }

    private fun bindTeamsRecyclerView() {
        binding.teams.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CardAdapter(filteredTeams) { _ ->
                findNavController().navigate(R.id.action_DashboardFragment_to_teamDetail)
            }
        }
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
                searchConfiguration(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_add) {
                    findNavController().navigate(R.id.action_DashboardFragment_to_newTeamFragment)
                }
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().popBackStack()
                }
                if(menuItem.itemId == R.id.action_singout){
                    showAlertSingUp()
                }

                return true
            }
        })
    }

    private fun searchConfiguration(menu: Menu) {
        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = (menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                search(query)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val menuHost: MenuHost = requireActivity()
        menuHost.invalidateMenu()
        _binding = null
    }

    @SuppressLint("NewApi")
    private fun search(query: String?) {
        filteredTeams.clear()
        filteredTeams.addAll(teams.filter { team: Team ->
            team.title.lowercase().contains(query.toString())
        })
        val adapter = binding.teams.adapter as CardAdapter
        adapter.filterList(filteredTeams)
    }

    override fun onStart() {
        super.onStart()
        populateList()
    }

    private fun populateList() {
        teams.clear()
        filteredTeams.clear()
        FirebaseApp.initializeApp(this.requireContext())
        val database = Firebase.database
        database.getReference("user").child("0/team").get().addOnSuccessListener { it ->
            val value: ArrayList<Long> = it.value as ArrayList<Long>
            for (i in value) {
                database.getReference("team").child(i.toString()).get().addOnSuccessListener { it ->
                    var title = it.child("title").value
                    var description = it.child("description").value
                    teams.add(
                        Team(
                            i.toInt(),
                            R.drawable.placeholder,
                            title.toString(),
                            description.toString()
                        )
                    )
                    filteredTeams.add(
                        Team(
                            i.toInt(),
                            R.drawable.placeholder,
                            title.toString(),
                            description.toString()
                        )
                    )
                    bindTeamsRecyclerView()

                }
            }

        }
    }

    private fun showAlertSingUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Cerrar sesion")
        builder.setMessage("Estas a punto de cerrar la sesion, estas seguro?")
        builder.setPositiveButton("Aceptar") { _, _ -> signOut() }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
            ?.edit()
            ?.clear()
            ?.apply()
        findNavController().navigate(R.id.action_DashboardFragment_to_authFragment)
    }

    private fun inSession() {
        val prefs = activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val email = prefs?.getString("email", null)

        if (email == null) {
            findNavController().navigate(R.id.action_DashboardFragment_to_authFragment)
        }
    }
}
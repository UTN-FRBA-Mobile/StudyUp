package com.studyup.classes.TeamDetail.bibliography

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.classes.NewTeam.bibliography.BibliographyRecycler
import com.studyup.classes.TeamDetail.TeamDetailSelected
import com.studyup.databinding.FragmentMembersListBinding

class bibliographyFragmentList: Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private val binding get() = _binding!!
    private var viewAdapter: bibliographyReciclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMembersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myDataset = TeamDetailSelected.selectedTeam.bibliography
            .map { BibliographyRecycler(it, false) } as MutableList<BibliographyRecycler>

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = bibliographyReciclerViewAdapter(myDataset)
        val viewAdapter = this.viewAdapter
        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notify_update() {
        this.viewAdapter?.myDataset = TeamDetailSelected.selectedTeam.bibliography
            .map { BibliographyRecycler(it, false) } as MutableList<BibliographyRecycler>
        this.viewAdapter?.notifyDataSetChanged()
    }
}
package com.studyup.classes.NewTeam.bibliography

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.api.APIService
import com.studyup.classes.TeamDetail.events.EventRecycler
import com.studyup.classes.TeamDetail.events.EventRecyclerViewAdapter
import com.studyup.databinding.FragmentMembersListBinding

class BibliographiesFragmentList : Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private val binding get() = _binding!!
    private var viewAdapter: BibliographyRecyclerViewAdapter? = null
    private var columnCount = 1
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

        val myDataset = APIService.getBibliographies().map{ BibliographyRecycler(it,false) } as MutableList<BibliographyRecycler>

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = BibliographyRecyclerViewAdapter(myDataset, this)
        val viewAdapter = this.viewAdapter
        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notify_update(){
        this.viewAdapter?.myDataset = APIService.getBibliographies().map{ BibliographyRecycler(it,false) } as MutableList<BibliographyRecycler>
        this.viewAdapter?.notifyDataSetChanged()
    }
}
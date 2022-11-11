package com.studyup.classes.TeamDetail.events

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.studyup.api.APIService
import com.studyup.databinding.FragmentMembersListBinding

/**
 * A fragment representing a list of Items.
 */
class EventsFragmentList : Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private val binding get() = _binding!!
    private var viewAdapter: EventRecyclerViewAdapter? = null
    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMembersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myDataset = APIService.getEvents().map{EventRecycler(it,false)} as MutableList<EventRecycler>

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = EventRecyclerViewAdapter(myDataset, this)
        val viewAdapter = this.viewAdapter
        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notify_update(){
        this.viewAdapter?.myDataset = APIService.getEvents().map{ EventRecycler(it,false) } as MutableList<EventRecycler>
        this.viewAdapter?.notifyDataSetChanged()
    }
}
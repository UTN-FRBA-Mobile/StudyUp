package com.studyup.classes.NewTeam.members

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.databinding.FragmentMembersListBinding
import com.studyup.utils.State

class MembersFragmentList : Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private var viewAdapter: MembersFragmentAdapter? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMembersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myDataset = State.newTeam.members

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = MembersFragmentAdapter(myDataset, this)
        val viewadapter = this.viewAdapter
        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewadapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notify_update(){
        this.viewAdapter?.notifyDataSetChanged()
    }
}
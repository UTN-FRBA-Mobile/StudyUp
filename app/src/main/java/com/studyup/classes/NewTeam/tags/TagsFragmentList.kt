package com.studyup.classes.NewTeam.tags

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.api.APIService
import com.studyup.classes.NewTeam.members.MembersFragmentAdapter
import com.studyup.databinding.FragmentMembersListBinding

class TagsFragmentList : Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private var viewAdapter: TagsFragmentAdapter? = null
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

        val myDataset = APIService.getTags()

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = TagsFragmentAdapter(myDataset, this)
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
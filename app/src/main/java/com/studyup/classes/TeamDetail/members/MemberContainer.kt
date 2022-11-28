package com.studyup.classes.one_team

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.api.Member
import com.studyup.databinding.FragmentTeamDetailMemberListBinding

class MemberContainer(private var myDatabase: MutableList<Member>? = null): Fragment() {
    private var _binding: FragmentTeamDetailMemberListBinding? = null
    private var viewAdapter: MemberContainerElement? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamDetailMemberListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewManager = LinearLayoutManager(this.context)
        this.viewAdapter = MemberContainerElement(myDatabase)
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
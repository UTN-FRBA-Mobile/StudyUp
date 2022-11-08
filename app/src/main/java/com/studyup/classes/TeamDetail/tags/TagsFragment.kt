package com.studyup.classes.TeamDetail.tags

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Tag
import com.studyup.classes.NewTeam.tags.TagRecycler
import com.studyup.classes.one_team.MemberContainerElement
import com.studyup.databinding.FragmentMembersListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TagsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TagsFragment (private var myDatabase: MutableList<Tag>? = null) : Fragment() {
    private var _binding: FragmentMembersListBinding? = null
    private var viewAdapter: TagsFragmentElement? = null

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

        val viewManager = LinearLayoutManager(this.context)
        val myDataset = myDatabase!!.map{TagRecycler(it,null, true,false)} as MutableList<TagRecycler>
        this.viewAdapter = TagsFragmentElement(myDataset, this)
        val viewadapter = this.viewAdapter
        recyclerView = binding.myRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewadapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notify_update(){
        this.viewAdapter?.myDataset = APIService.getTags().map{TagRecycler(it,null, true,false)} as MutableList<TagRecycler>
        this.viewAdapter?.notifyDataSetChanged()
    }
}
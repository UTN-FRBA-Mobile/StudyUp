package com.studyup.classes.TeamDetail.tags

import android.app.Dialog
import android.icu.math.BigDecimal
import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyup.R
import com.studyup.api.Activity
import com.studyup.api.Member
import com.studyup.api.Team
import com.studyup.classes.one_team.MemberContainerElement
import com.studyup.classes.team.CardAdapter
import com.studyup.classes.team.filteredTeams
import com.studyup.classes.team.teams
import com.studyup.databinding.FragmentMembersListBinding
import com.studyup.databinding.FragmentTeamDetailDialogBinding
import java.security.Key
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

class TagsFragmentDialog (private val recyler: TagsFragment= TagsFragment()): Fragment() {
    private var _binding: FragmentTeamDetailDialogBinding? = null
    private val binding get() = _binding!!
    private var filteredMembers =  mutableListOf<Member>()
    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamDetailDialogBinding.inflate(inflater, container, false)
        binding.dialogClose.setOnClickListener {
            var detailsFrame = recyler.requireActivity().findViewById<View>(R.id.dialogContainer)
            detailsFrame?.visibility = View.GONE
            detailsFrame = recyler.requireActivity().findViewById<View>(R.id.my_recycler_view)
            detailsFrame?.visibility = View.VISIBLE
            val transaction =   recyler.childFragmentManager.beginTransaction()
            transaction
                .replace(R.id.dialogContainer, DialogFragment())
                .addToBackStack(null)
                .commit()


        }

        return binding.root
    }
    private fun search(query: String?, activity: Activity) {
        filteredMembers.clear()
        filteredMembers.addAll(activity.memberComplete.filter { it.memberName.lowercase().contains(query.toString()) })
        val adapter = binding.dialogMembers.adapter as TagsFragmentDialogMembers
        adapter.filterList(filteredMembers)
    }

    override fun onStart() {

        val activity : Activity? =
            arguments?.getInt("idTag")
                ?.let { Team().getActivity(arguments?.getInt("idActivity")!!, it) }
        binding.dialogActivityName.text = arguments?.getString("title")
        if (activity != null) {
            val memberCompleteSize: Double= activity.memberComplete.size.toDouble()
            binding.percentComplete.text = memberCompleteSize.div(Team().getTotalMembers().toDouble()).times(100).roundToInt().toString() + "%"
            binding.percentIncomplete.text = ((1 - memberCompleteSize.div(Team().getTotalMembers().toDouble())).times(100)).roundToInt().toString() + "%"
            recyclerView = binding.dialogMembers.apply {
                layoutManager =  LinearLayoutManager(this.context)
                adapter = TagsFragmentDialogMembers(activity.memberComplete)
            }
            binding.searchMember.editText?.doOnTextChanged { inputText, _, _, _ ->

                        search(binding.searchMember.editText?.text.toString(), activity)

                }

        }
        super.onStart()
    }
}
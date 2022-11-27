package com.studyup.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Bibliography
import com.studyup.api.Event
import com.studyup.api.Team
import com.studyup.classes.NewTeam.bibliography.BibliographiesFragment
import com.studyup.classes.TeamDetail.events.EventsFragment
import com.studyup.classes.TeamDetail.tags.TagsFragment
import com.studyup.classes.TeamDetailAdapter
import com.studyup.classes.one_team.MemberContainer
import com.studyup.databinding.FragmentTeamDetailBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TeamDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var teamDetailAdapter: TeamDetailAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        toolbarMenuSetup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById(R.id.pager) as ViewPager2
        val mypager = TeamDetailAdapter(requireActivity())
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)

        addViewPagerFragments(mypager, viewPager)
        addIconsForEachTab(tabLayout, viewPager)
    }

    private fun addIconsForEachTab(
        tabLayout: TabLayout,
        viewPager: ViewPager2
    ) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //TODO: Refactor pls, I'm ashamed of this
            when (position) {
                0 -> tab.setIcon(R.drawable.people)
                1 -> tab.setIcon(R.drawable.tag)
                2 -> tab.setIcon(R.drawable.calendar)
                3 -> tab.setIcon(R.drawable.book)
                else -> tab.text = "Not a tab"
            }
        }.attach()
    }

    private fun addViewPagerFragments(
        mypager: TeamDetailAdapter,
        viewPager: ViewPager2
    ) {
        val testEvents: MutableList<Event> = APIService.getTestEvents()
        val testBibliographies: MutableList<Bibliography> = APIService.getTestBibliographies()
        mypager.addFragment(MemberContainer(Team().members))
        mypager.addFragment(TagsFragment(Team().tags))
        mypager.addFragment(EventsFragment(testEvents))
        mypager.addFragment(BibliographiesFragment(testBibliographies))
        viewPager.adapter = mypager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.action_search)
                menu.removeItem(R.id.action_add)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().navigate(R.id.action_newTeamFragment_to_DashboardFragment)
                }
                return true
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
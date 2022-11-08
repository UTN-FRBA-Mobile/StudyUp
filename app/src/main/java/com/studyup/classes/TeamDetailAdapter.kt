package com.studyup.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.studyup.fragments.TeamDetailObject

public const val ARG_OBJECT = "object"

class TeamDetailAdapter(manager: FragmentActivity): FragmentStateAdapter(manager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    fun addFragment(newFragment: Fragment){
        fragmentList.add(newFragment)
    }
}
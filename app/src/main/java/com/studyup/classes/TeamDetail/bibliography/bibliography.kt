package com.studyup.classes.TeamDetail.bibliography

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studyup.R
import com.studyup.api.Bibliography
import com.studyup.databinding.FragmentBibliographiesBinding

class bibliography(private var bibliographies: MutableList<Bibliography>? = null) :
    Fragment() {
    private lateinit var _binding: FragmentBibliographiesBinding
    private var fragmentRecycler: bibliographyFragmentList? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentRecycler = bibliographyFragmentList()
        val fragment = this.fragmentRecycler
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commitNow()
        }

        _binding = FragmentBibliographiesBinding.inflate(inflater, container, false)
        _binding.ArrowAdd.visibility = View.GONE
        this.fragmentRecycler!!.notify_update()
        return _binding.root
    }

    override fun onStart() {
        super.onStart()
        this.fragmentRecycler!!.notify_update()
    }
}
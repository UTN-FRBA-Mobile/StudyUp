package com.studyup.classes.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studyup.databinding.FragmentMembersEmptyBinding

class MembersEmpty:Fragment() {
    private var _binding: FragmentMembersEmptyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMembersEmptyBinding.inflate(inflater, container, false)
        return binding.root

    }
}
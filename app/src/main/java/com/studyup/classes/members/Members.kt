package com.studyup.classes.members

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.studyup.MainActivity
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Member
import com.studyup.databinding.FragmentMembersBinding
import com.studyup.databinding.FragmentSecondBinding

class Members: Fragment() {
    private lateinit var _binding: FragmentMembersBinding
    private var fragmentRecicler: MembersFragmentList? = null

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireActivity(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        val alloptions: MutableList<Member> =
            APIService.getMembersAll(_binding.filledTextField.editText?.text.toString())
        for (user in alloptions) {
            popup.menu.add(user.memberName)
        }
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            APIService.insertMember(item.title.toString())
            if (this.fragmentRecicler == null
            ) {
                this.fragmentRecicler = MembersFragmentList()
                val fragment = this.fragmentRecicler
                if (fragment != null) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commitNow()
                }
            }
            this.fragmentRecicler!!.notify_update()
            _binding.filledTextField.editText?.setText("")
            true
        })
        popup.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var members = APIService.getMembers()
        if(members.size!=0){
            this.fragmentRecicler = MembersFragmentList()
            val fragment = this.fragmentRecicler
            if (fragment != null) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commitNow()
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.action_search)
                menu.removeItem(R.id.action_add)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().navigate(R.id.action_MembersMain_to_SecondFragment)
                return true
            }
        })
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        val contextMenuTextView = _binding.root.findViewById<TextInputLayout>(R.id.filledTextField)
        _binding.filledTextField.editText?.doOnTextChanged { inputText, _, _, _ ->
            _binding.filledTextField.error = null
            if (inputText.toString() != "")
                showMenu(contextMenuTextView, R.menu.menu_members)
        }
        _binding.ArrowAdd.setOnClickListener { view ->
            var text_input = _binding.filledTextField.editText?.text.toString()
            if (text_input == "")
                _binding.filledTextField.error = "Completar campo"
            else {
                try {
                    APIService.insertMember(text_input)
                    if (this.fragmentRecicler == null
                    ) {
                        this.fragmentRecicler = MembersFragmentList()
                        val fragment = this.fragmentRecicler
                        if (fragment != null) {
                            childFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, fragment)
                                .commitNow()
                        }
                    }
                    this.fragmentRecicler!!.notify_update()
                    _binding.filledTextField.editText?.setText("")
                } catch (e: Exception) {
                    _binding.filledTextField.error = "Usuario no encontrado"
                }

                true
            }
        }
        return _binding.root

    }
}
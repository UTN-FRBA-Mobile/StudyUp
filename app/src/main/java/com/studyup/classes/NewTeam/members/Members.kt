package com.studyup.classes.NewTeam.members

import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Member
import com.studyup.databinding.FragmentMembersBinding
import com.studyup.exceptions.MemberAlreadyExists
import com.studyup.exceptions.MemberNotFound
import com.studyup.utils.State

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
            addMember(item.title.toString())
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
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().navigate(R.id.action_MembersMain_to_newTeamFragment)
                }
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
        _binding.ArrowAdd.setOnClickListener { _ ->
            var text_input = _binding.filledTextField.editText?.text.toString()
            if (text_input == "")
                _binding.filledTextField.error = "Completar campo"
            else {
                try {
                    addMember(text_input)
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
                } catch (e: MemberNotFound) {
                    _binding.filledTextField.error = "Usuario no encontrado"
                }catch (e: MemberAlreadyExists){
                    _binding.filledTextField.error = "Usuario ya asignado"
                }
            }
        }
        return _binding.root

    }

    private fun addMember(text_input: String) {
        APIService.insertMember(text_input)
        State.newTeam.addMember(Member(text_input, "", false))
    }
}
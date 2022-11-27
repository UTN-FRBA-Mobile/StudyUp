package com.studyup.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studyup.R
import com.studyup.databinding.FragmentAuthBinding

class Auth : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.signUpButton.setOnClickListener {
            signUp()
        }

        _binding!!.loginButton.setOnClickListener {
            login()
        }
    }

    private fun signUp() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!validateEmail(email, password)) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                showHome()
                val user = auth.currentUser
                updateUI(user)
            } else {
                showAlert()
                updateUI(null)
            }
        }

    }

    private fun login(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!validateEmail(email, password)) {
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                showHome()
                val user = auth.currentUser
                //updateUI(user)
            } else {
                showAlert()
                //updateUI(null)
            }
        }
    }

    private fun validateEmail(email: String, password: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            binding.emailEditText.error = "Requerido"
            valid = false
        } else {
            binding.emailEditText.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.passwordEditText.error = "Requerido"
            valid = false
        } else {
            binding.passwordEditText.error = null
        }

        return valid
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(){
        findNavController().navigate(R.id.action_AuthFragment_to_dashboardFragment)
    }

    private fun updateUI(user: FirebaseUser?) {
        /*if (user != null) {
            binding.status.text = getString(R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified)
            binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)

            binding.emailPasswordButtons.visibility = View.GONE
            binding.emailPasswordFields.visibility = View.GONE
            binding.signedInButtons.visibility = View.VISIBLE

            if (user.isEmailVerified) {
                binding.verifyEmailButton.visibility = View.GONE
            } else {
                binding.verifyEmailButton.visibility = View.VISIBLE
            }
        } else {
            binding.status.setText(R.string.signed_out)
            binding.detail.text = null

            binding.emailPasswordButtons.visibility = View.VISIBLE
            binding.emailPasswordFields.visibility = View.VISIBLE
            binding.signedInButtons.visibility = View.GONE
        }*/
    }

}
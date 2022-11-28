package com.studyup.fragments

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studyup.R
import com.studyup.databinding.FragmentAuthBinding

class Auth : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var signInClient: SignInClient

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            handleSignInResult(result.data)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarMenuSetup()

        auth = Firebase.auth
        signInClient = Identity.getSignInClient(requireContext())

        _binding!!.signUpButton.setOnClickListener {
            signUp()
        }

        _binding!!.loginButton.setOnClickListener {
            loginWithEmail()
        }

        _binding!!.googleButton.setOnClickListener {
            loginWithGoogle()
        }

    }

    //EMAIL

    private fun loginWithEmail() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!validateEmail(email, password)) {
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                showHome()
            } else {
                showAlert()
                updateUI(null)
            }
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
                val user = auth.currentUser
                updateUI(user)
                showHome()
            } else {
                showAlert()
                updateUI(null)
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

    //GOOGLE

    private fun loginWithGoogle() {
        val signInRequest = GetSignInIntentRequest.builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        signInClient.getSignInIntent(signInRequest)
            .addOnSuccessListener { pendingIntent ->
                launchSignIn(pendingIntent)
            }
    }

    private fun launchSignIn(pendingIntent: PendingIntent) {
        val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent)
            .build()
        signInLauncher.launch(intentSenderRequest)
    }

    private fun handleSignInResult(data: Intent?) {
        try {
            val credential = signInClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                firebaseAuthWithGoogle(idToken)
            }
        } catch (e: ApiException) {
            updateUI(null)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    showHome()
                } else {
                    showAlert()
                    updateUI(null)
                }
            }
    }

    //SHOW

    private fun showAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {
        findNavController().navigate(R.id.action_AuthFragment_to_dashboardFragment)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            user.email?.let { saveSession(it) }
        }else{
            deleteSession()
        }
    }

    //SESSION

    private fun saveSession(email: String) {
        activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)?.edit()
            ?.putString("email", email)
            ?.apply()
    }

    private fun deleteSession() {
        activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
            ?.edit()
            ?.clear()
            ?.apply()
    }

    private fun toolbarMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.action_search)
                menu.removeItem(R.id.action_add)
                menu.removeItem(R.id.action_singout)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

}
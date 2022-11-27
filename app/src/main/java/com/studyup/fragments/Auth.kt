package com.studyup.fragments

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
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

enum class ProviderType {
    BASIC, GOOGLE
}

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

        auth = Firebase.auth
        signInClient = Identity.getSignInClient(requireContext())

        //session()

        _binding!!.signUpButton.setOnClickListener {
            signUp()
        }

        _binding!!.loginButton.setOnClickListener {
            login()
        }

        _binding!!.googleButton.setOnClickListener {
            login(ProviderType.GOOGLE)
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            oneTapSignIn()
        }
    }

    private fun oneTapSignIn() {
        val oneTapRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

        // Display the One Tap UI
        signInClient.beginSignIn(oneTapRequest)
            .addOnSuccessListener { result ->
                launchSignIn(result.pendingIntent)
            }
            .addOnFailureListener { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
            }
    }

    private fun login(providerType: ProviderType? = null) {
        when (providerType) {
            ProviderType.GOOGLE -> loginWithGoogle()
            else -> {
                loginWithEmail()
            }
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
                updateUI(user, ProviderType.BASIC)
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
                updateUI(user, ProviderType.BASIC)
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

    private fun updateUI(user: FirebaseUser?, providerType: ProviderType? = null) {

        if (user != null) {
            user.email?.let { saveSession(it, "BASIC") }
        }

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

    //SESSION

    private fun saveSession(email: String, provider: String) {
        activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)?.edit()
            ?.putString("email", email)
            ?.putString("provider", provider)
            ?.apply()
    }

    private fun deleteSession() {
        activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)?.edit()
            ?.clear()
            ?.apply()
    }

    private fun session() {
        val prefs = activity?.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val email = prefs?.getString("email", null)
        val provider = prefs?.getString("provider", null)

        if (email != null && provider != null) {
            showHome()
        }
    }

}
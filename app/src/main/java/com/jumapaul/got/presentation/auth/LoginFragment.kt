package com.jerimkaura.got.presentation.auth

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentLoginBinding
import com.jerimkaura.got.databinding.SendEmailVerificationEmailDialogBinding
import com.jerimkaura.got.util.showAlert

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var successDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog
    private lateinit var sendEmailDialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        buildLoadingDialog()
        buildSuccessDialog()
        buildReSendVerificationEmailDialog()

        val emailTextLayout = binding.layoutEmail
        val passwordLayout = binding.layoutPassword
        val loginButton = binding.btnLogin
        binding.requestVerificationLink.setOnClickListener {
            showEmailDialog()
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTextLayout.error = "Enter a valid Email Address"
            } else if (password.isEmpty() && password.length < 5) {
                passwordLayout.error = "Password must be at least 8 characters"
            } else {
                loginUserWithEmailAndPassword(email, password)
            }
        }
    }

    private fun buildReSendVerificationEmailDialog() {
        val dialogMainBinding: SendEmailVerificationEmailDialogBinding =
            SendEmailVerificationEmailDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogMainBinding.root)
        builder.setCancelable(false)
        dialogMainBinding.btnSubmit.setOnClickListener {
            val email = dialogMainBinding.email.text.toString()
            val password = dialogMainBinding.password.text.toString()
            val passwordLayout = dialogMainBinding.layoutPassword
            val emailTextLayout = dialogMainBinding.layoutEmail

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTextLayout.error = "Enter a valid Email Address"
            } else if (password.isEmpty() && password.length < 5) {
                passwordLayout.error = "Password must be at least 8 characters"
            } else {
                showAlert(requireContext(), "ERRO")
               resendVerificationEmail(email, password)
            }
        }

        dialogMainBinding.btnCancel.setOnClickListener {
            dismissEmailDialog()
        }
        sendEmailDialog = builder.create()
    }

    private fun dismissEmailDialog() {
        sendEmailDialog.dismiss()
    }

    private fun showEmailDialog() {
        sendEmailDialog.show()
    }


    private fun resendVerificationEmail(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                sendVerificationEmail()
                FirebaseAuth.getInstance().signOut()
                sendEmailDialog.dismiss()
            }else{
               showAlert(requireContext(), "Send email failed")
            }
        }.addOnFailureListener {
            showAlert(requireContext(), "Error ${it.localizedMessage}")
        }
    }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isComplete) {
                showAlert(requireContext(), "Verification link sent to your email")
            } else {
                showAlert(requireContext(), "Could not send verification link")
            }
        }
    }

    private fun loginUserWithEmailAndPassword(email: String, password: String) {
        showLoadingDialog()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showAlert(requireContext(), "Logged in")
                    dismissLoadingDialog()
                    if (profileNameSet()){
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }else{
                        val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
                        findNavController().navigate(action)
                    }
                }else{
                    dismissLoadingDialog()
                    Log.d("LOGIN ERROR", "Error")
                }
            }.addOnFailureListener {
                showAlert(requireContext(), "Error ${it.localizedMessage}")
            }
    }

    @SuppressLint("InflateParams")
    private fun buildSuccessDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog_layout, null))
        builder.setCancelable(true)
        loadingDialog = builder.create()
    }

    @SuppressLint("InflateParams")
    private fun buildLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.success_dialog_layout, null))
        builder.setCancelable(true)
        successDialog = builder.create()
    }

    @SuppressLint("InflateParams")
    private fun buildErrorDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.error_dialog_layout, null))
        builder.setCancelable(true)
        errorDialog = builder.create()
    }


    private fun showSuccessDialog() {
        successDialog.show()
    }

    private fun showLoadingDialog() {
        loadingDialog.show()
    }

    private fun showErrorDialog() {
        errorDialog.show()
    }


    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun dismissSuccessDialog() {
        successDialog.dismiss()
    }


    private fun dismissErrorDialog() {
        errorDialog.dismiss()
    }

    private fun profileNameSet(): Boolean {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )

        val name = pref.getString(getString(R.string.profile_name_key),
            getString(R.string.profile_name_default)
        )

        return name != getString(R.string.profile_name_default) && name != ""
    }
}
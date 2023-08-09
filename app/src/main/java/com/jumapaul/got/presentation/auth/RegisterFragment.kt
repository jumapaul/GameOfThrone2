package com.jerimkaura.got.presentation.auth


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentRegisterBinding
import com.jerimkaura.got.util.showAlert


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var successDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        buildLoadingDialog()
        buildSuccessDialog()
        buildErrorDialog()

        val registerButton = binding.btnRegister
        val emailTextLayout = binding.layoutEmail
        val passwordLayout = binding.layoutPassword
        val confirmPasswordLayout = binding.layoutPassword
        binding.loginText.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }


        registerButton.setOnClickListener {
            val email = binding.teEmail.text.toString()
            val password = binding.tePassword.text.toString()
            val confirmPassword = binding.teConfirmPassword.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTextLayout.error = "Enter a valid Email Address"
            } else if (password.isEmpty() && password.length < 5) {
                passwordLayout.error = "Password must be at least 8 characters"
            } else if (confirmPassword.isEmpty() && password.length < 5) {
                confirmPasswordLayout.error = "Password must match confirm password"
            } else if (password != confirmPassword) {
                showAlert(requireContext(), "Password & confirm password are no the same")
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        showLoadingDialog()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    sendVerificationEmail()
                    dismissLoadingDialog()
                    showSuccessDialog()
                    Handler().postDelayed({
                        dismissSuccessDialog()
                    }, 2000)
                    FirebaseAuth.getInstance().signOut()
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(action)
                } else {
                    Log.d("TAG", "Register error:" + FirebaseAuth.getInstance().currentUser?.uid)
                    dismissLoadingDialog()
                }
            }.addOnFailureListener {
                showAlert(requireContext(), "Failed ${it.localizedMessage}")
            }
    }


    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                showAlert(requireContext(), "Verification link sent to your email")
            } else {
                showAlert(requireContext(), "Could not send verification link")
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun buildSuccessDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.success_dialog_layout, null))
        builder.setCancelable(true)
        successDialog = builder.create()
    }

    @SuppressLint("InflateParams")
    private fun buildLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog_layout, null))
        builder.setCancelable(true)
        loadingDialog = builder.create()
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
        errorDialog.dismiss()
        loadingDialog.dismiss()
        successDialog.show()
    }

    private fun showLoadingDialog() {
        errorDialog.dismiss()
        successDialog.dismiss()
        loadingDialog.show()
    }


    private fun dismissLoadingDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    private fun dismissSuccessDialog() {
        if (successDialog.isShowing) {
            successDialog.dismiss()
        }
    }


}
package com.jerimkaura.got.presentation.onboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentSplashBinding
import com.jerimkaura.got.util.showAlert

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        if (onBoardingFinished()) {
            setupFireBaseAuth()
        } else {
            val action = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
            findNavController().navigate(action)
        }
    }
    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun profileNameSet(): Boolean {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )

        val name = pref.getString(
            getString(R.string.profile_name_key),
            getString(R.string.profile_name_default)
        )

        return name != getString(R.string.profile_name_default) && name != ""
    }


    private fun setupFireBaseAuth() {
        FirebaseAuth.getInstance().addAuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            if (user != null) {
                if (user.isEmailVerified) {
                    if (profileNameSet()) {
                        val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                        findNavController().navigate(action)
                    } else {
                        val action =
                            SplashFragmentDirections.actionSplashFragmentToProfileFragment()
                        findNavController().navigate(action)
                    }
                } else {
                    FirebaseAuth.getInstance().signOut()
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    findNavController().navigate(action)
                    showAlert(requireContext(), "Please verify account to proceed")
                }
            } else {
                Log.d("TAG", "USER IS NULL: ")
            }
        }
    }
}
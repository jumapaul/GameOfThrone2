package com.jerimkaura.got.presentation.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onStart() {
        super.onStart()
        checkAuthenticationState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val name = binding.txtName

        val pref: SharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )


        binding.txtName.setText(
            pref.getString(
                getString(R.string.profile_name_key),
                getString(R.string.profile_name_default)
            )
        )

        binding.btnSave.setOnClickListener {
            pref.edit()
                .putString(getString(R.string.profile_name_key), name.text?.trim().toString())
                .apply()
            val action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        } else {
            Log.e("TAG", "checkAuthenticationState")
        }

    }


}
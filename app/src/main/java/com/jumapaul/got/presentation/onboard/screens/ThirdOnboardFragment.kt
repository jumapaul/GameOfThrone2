package com.jerimkaura.got.presentation.onboard.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentThirdOnboardBinding
import com.jerimkaura.got.presentation.onboard.ViewPagerFragmentDirections

class ThirdOnboardFragment : Fragment(R.layout.fragment_third_onboard) {
    private lateinit var binding: FragmentThirdOnboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdOnboardBinding.bind(view)

        binding.btnNext.setOnClickListener {
            onBoardingFinished()
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("Finished", true).apply()
    }
}
package com.jerimkaura.got.presentation.onboard.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentSecondOnboardBinding

class SecondOnboardFragment : Fragment(R.layout.fragment_second_onboard) {
    private lateinit var binding: FragmentSecondOnboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondOnboardBinding.bind(view)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboard_view_pager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

}
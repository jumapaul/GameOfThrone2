package com.jerimkaura.got.presentation.onboard.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentFirstOnboardBinding
import com.jerimkaura.got.databinding.FragmentViewPagerBinding

class FirstOnboardFragment : Fragment(R.layout.fragment_first_onboard) {
    private lateinit var binding: FragmentFirstOnboardBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstOnboardBinding.bind(view)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboard_view_pager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

    }

}
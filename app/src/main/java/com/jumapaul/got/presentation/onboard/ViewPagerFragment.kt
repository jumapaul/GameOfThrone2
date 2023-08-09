package com.jerimkaura.got.presentation.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentViewPagerBinding
import com.jerimkaura.got.presentation.onboard.screens.FirstOnboardFragment
import com.jerimkaura.got.presentation.onboard.screens.SecondOnboardFragment
import com.jerimkaura.got.presentation.onboard.screens.ThirdOnboardFragment

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {
    private lateinit var binding: FragmentViewPagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPagerBinding.bind(view)

        val fragmentList = arrayListOf(
            FirstOnboardFragment(),
            SecondOnboardFragment(),
            ThirdOnboardFragment()
        )

        val adapter = OnBoardViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.onboardViewPager.adapter = adapter
        binding.onboardViewPager.removeView(view)
    }

}
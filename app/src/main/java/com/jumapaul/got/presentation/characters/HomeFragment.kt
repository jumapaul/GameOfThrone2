package com.jerimkaura.got.presentation.characters

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.jerimkaura.got.R
import com.jerimkaura.got.databinding.FragmentHomeBinding
import com.jerimkaura.got.presentation.auth.ProfileFragmentDirections
import com.jerimkaura.got.presentation.continents.ContinentsAdapter
import com.jerimkaura.got.presentation.viewmodel.CharactersViewModel
import com.jerimkaura.got.presentation.viewmodel.ContinentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var name: String
    private val charactersViewModel: CharactersViewModel by viewModels()
    private val continentsViewModel: ContinentsViewModel by viewModels()
    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }
    private val continentsAdapter: ContinentsAdapter by lazy {
        ContinentsAdapter()
    }

    private val familyAdapter: FamilyAdapter by lazy {
        FamilyAdapter()
    }
    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.apply {
            greetingText.text = getGreeting()[0]
            nameText.text = getUserName()
            profileLetter.text = getNameInitials(getUserName())
            profileLetter.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                findNavController().navigate(action)
            }
            welcomeMessageText.text = getGreeting()[1]
        }


        continentsViewModel.continents.observe(viewLifecycleOwner) { continents ->
            continentsAdapter.submitList(continents.data)
        }

        binding.rvContinents.apply {
            adapter = continentsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        charactersViewModel.characters.observe(viewLifecycleOwner) { result ->
            charactersAdapter.submitList(result.data)
        }

        binding.rvCharacters.apply {
            adapter = charactersAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvFamilies.apply {
            adapter = familyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun getUserName(): String {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )

        return pref.getString(
            getString(R.string.profile_name_key),
            getString(R.string.profile_name_default)
        )!!
    }

    private fun getGreeting(): ArrayList<String> {
        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date

        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val greeting: String
        val message: String

        if (hour in 12..16) {
            greeting = getString(R.string.afternoon_greeting)
            message = getString(R.string.afternoon_message)

        } else if (hour in 17..20) {
            greeting = getString(R.string.evening_greeting)
            message = getString(R.string.evening_message)
        } else if (hour in 21..23) {
            greeting = getString(R.string.night_greeting)
            message = getString(R.string.night_message)
        } else {
            greeting = getString(R.string.morning_greeting)
            message = getString(R.string.morning_message)
        }
        return arrayListOf(greeting, message)
    }

    private fun getNameInitials(name: String): String {
        val firstName = name.split("\\s".toRegex()).first()[0]
        var lastName = name.split("\\s".toRegex()).getOrNull(1)
        return if (lastName != null) {
            lastName = lastName[0].toString()
            firstName.plus(lastName).uppercase()
        } else {
            firstName.plus(name.split("\\s".toRegex()).first()[1].toString()).uppercase()
        }
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
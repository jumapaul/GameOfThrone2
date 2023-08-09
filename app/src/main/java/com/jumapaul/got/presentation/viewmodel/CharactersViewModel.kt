package com.jerimkaura.got.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jerimkaura.got.data.local.entities.Character
import com.jerimkaura.got.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
   private val repository: CharactersRepository
) : ViewModel() {
    val characters = repository.getCharacters().asLiveData()

    fun getCharacterById(id: Int): LiveData<Character> = repository.getCharacterById(id)
}
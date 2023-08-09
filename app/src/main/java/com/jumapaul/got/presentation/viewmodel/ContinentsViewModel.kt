package com.jerimkaura.got.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jerimkaura.got.domain.repository.ContinentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(repository: ContinentsRepository) :
    ViewModel() {
    val continents = repository.getContinents().asLiveData()
}
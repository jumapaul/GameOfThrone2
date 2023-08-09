package com.jerimkaura.got.domain.usecase

import com.jerimkaura.got.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository){

}
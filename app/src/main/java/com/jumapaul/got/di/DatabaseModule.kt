package com.jerimkaura.got.di

import com.jerimkaura.got.data.local.ThronesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideCharactersDao(database: ThronesDatabase) = database.characterDao()

    @Singleton
    @Provides
    fun provideContinentsDao(database: ThronesDatabase) = database.continentsDao()
}
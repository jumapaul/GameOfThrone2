package com.jerimkaura.got.di

import android.content.Context
import androidx.room.Room
import com.jerimkaura.got.data.local.ThronesDatabase
import com.jerimkaura.got.data.remote.ThronesApi
import com.jerimkaura.got.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideThronesApi(retrofit: Retrofit): ThronesApi = retrofit.create(ThronesApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ThronesDatabase {
        return Room.databaseBuilder(
            context,
            ThronesDatabase::class.java,
            "thrones_database"
        ).build()
    }
}
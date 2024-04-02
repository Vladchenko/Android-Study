package com.example.vladislav.androidstudy.services.playaudio.di

import android.content.Context
import android.media.MediaPlayer
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.services.playaudio.dispatchers.CoroutinesDispatchers
import com.example.vladislav.androidstudy.services.playaudio.dispatchers.CoroutinesDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class PlayAudioModule {

    @Singleton
    @Provides
    fun provideCoroutinesDispatchers(): CoroutinesDispatchers = CoroutinesDispatchersImpl()

    @Provides
    @Singleton
    fun provideMediaPlayer(context: Context) = MediaPlayer.create(context, R.raw.linkin_park_numb)
}
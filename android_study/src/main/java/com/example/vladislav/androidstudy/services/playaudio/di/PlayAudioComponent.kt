package com.example.vladislav.androidstudy.services.playaudio.di

import android.content.Context
import com.example.vladislav.androidstudy.services.playaudio.PlayAudioService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PlayAudioModule::class])
interface PlayAudioComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): PlayAudioComponent
    }
    fun inject(service: PlayAudioService)
}
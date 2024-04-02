package com.example.vladislav.androidstudy.services.playaudio

/** Abstracts a player functionality. */
interface Player {
    /** Plays audio. */
    fun play()

    /** Pauses audio. */
    fun pause()

    /** Stops audio. */
    fun stop()
}
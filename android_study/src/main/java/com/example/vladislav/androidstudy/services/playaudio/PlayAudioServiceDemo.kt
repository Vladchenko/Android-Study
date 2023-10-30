package com.example.vladislav.androidstudy.services.playaudio

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.vladislav.androidstudy.R

/**
 * Plain simple audio playing service implementation.
 * It doesn't concern about multithreading or any development guidelines.
 */
class PlayAudioServiceDemo : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val binder: IBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.linkin_park_numb)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        mediaPlayer.start()
        return binder
    }

    class LocalBinder : Binder() {
        // Return this instance so clients can call public methods
        val service: PlayAudioServiceDemo
            get() = PlayAudioServiceDemo()
    }
}

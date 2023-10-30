package com.example.vladislav.androidstudy.services.playaudio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.vladislav.androidstudy.R

/**
 * {@link android.app.Service} demo to play an audio track.
 */
class PlayAudioActivity : ComponentActivity() {

    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_audio)
        playButton = findViewById(R.id.play_button)
        playButton.setOnClickListener {
            startService(Intent(this, PlayAudioServiceDemo::class.java))
            // TODO bindService(Intent(this, PlayAudioService::class.java), )
        }
    }
}
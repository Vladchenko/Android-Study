package com.example.vladislav.androidstudy.services.playaudio

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.vladislav.androidstudy.R

/** Plays an audio track. */
class PlayAudioActivity : ComponentActivity() {

    private var bound: Boolean = false

    private var player: Player? = null
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private lateinit var pauseButton: Button

    /** Defines callbacks for service binding, passed to bindService().  */
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to PlayAudioService, cast the IBinder and get PlayAudioServiceDemo instance.
            if (!bound) {
                val binder = service as PlayAudioService.LocalBinder
                player = binder.serviceInstance
                bound = true
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            player = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_audio)
        initButtons()
    }

    private fun initButtons() {
        playButton = findViewById(R.id.play_button)
        pauseButton = findViewById(R.id.pause_button)
        stopButton = findViewById(R.id.stop_button)
        playButton.setOnClickListener {
            if (bound) {
                player?.play()
            }
        }
        pauseButton.setOnClickListener {
            if (bound) {
                player?.pause()
            }
        }
        stopButton.setOnClickListener {
            if (bound) {
                player?.stop()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, PlayAudioService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )
//            startService(Intent(this, PlayAudioServiceDemo::class.java))
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
        bound = false
    }
}
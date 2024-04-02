package com.example.vladislav.androidstudy.services.playaudio

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.vladislav.androidstudy.MyApplication
import com.example.vladislav.androidstudy.services.playaudio.dispatchers.CoroutinesDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Audio playing service implementation. */
class PlayAudioService : Service(), Player {

    private val binder: IBinder = LocalBinder()
    private val scope by lazy { CoroutineScope(SupervisorJob() + dispatchers.IO) }

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var dispatchers: CoroutinesDispatchers

    override fun onCreate() {
        (application as MyApplication).playAudioComponent.inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY // Service will NOT be restarted if it's killed by Android
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        scope.coroutineContext.job.cancel()
    }

    override fun play() {
        scope.launch {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }
    }

    override fun pause() {
        scope.launch {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }
    }

    override fun stop() {
        scope.launch {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }

    inner class LocalBinder : Binder() {
        // Return this instance so clients can call public methods
        val serviceInstance: PlayAudioService
            get() = this@PlayAudioService
    }
}

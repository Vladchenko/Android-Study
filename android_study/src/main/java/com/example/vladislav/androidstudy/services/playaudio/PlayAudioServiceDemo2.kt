package com.example.vladislav.androidstudy.services.playaudio

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.vladislav.androidstudy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Simple audio playing service implementation.
 */
// One cannot pass CoroutineDispatchers into constructor of Service, unlike into Repository or Interactor. Since it is
// an android framework class and it will throw an exception. Instead, seems it has to be created locally.
class PlayAudioServiceDemo2 : Service() {

    private val job = SupervisorJob()
    private val binder: IBinder = LocalBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.linkin_park_numb)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Выполняем асинхронную операцию внутри Coroutines
        scope.launch {
            mediaPlayer.start()
            stopSelf() // Останавливаем сервис после выполнения операции
        }

        // Возвращаем флаг, указывающий, что сервис должен быть перезапущен, если он был убит системой
        return START_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        mediaPlayer.start()
        return binder
    }

    inner class LocalBinder : Binder() {
        // Return this instance so clients can call public methods
        val service: PlayAudioServiceDemo2
            get() = this@PlayAudioServiceDemo2
    }
}

package com.example.vladislav.androidstudy.services.playaudio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.vladislav.androidstudy.MyApplication
import com.example.vladislav.androidstudy.services.playaudio.dispatchers.CoroutinesDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PlayAudioService"


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
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: starting foreground service")
        try {
            ServiceCompat.startForeground(
                this, NOTIFICATION_ID, buildNotification().build(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
            Log.d(TAG, "startForeground: SUCCESS")
        } catch (e: Exception) {
            Log.e(TAG, "startForeground failed", e)
        }
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
        scope.cancel()
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
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

    // Just a sample
    private fun buildNotification(time: String = "00:00:00"): NotificationCompat.Builder {
        val intent = Intent(this, PlayAudioActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Текущее время: $time")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Фоновый сервис",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    inner class LocalBinder : Binder() {
        // Return this instance so clients can call public methods
        val serviceInstance: PlayAudioService
            get() = this@PlayAudioService
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "time_channel"
    }
}

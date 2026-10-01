This solution plays one audio file from raw resource folder, having 3 buttons - play, pause, stop.

PlayAudioService is injected with Coroutine dispatchers and MediaPlayer from Dagger and receives callbacks
from PlayAudioActivity on commands for audio playback (play/pause/stop).

As for PlayAudioService implementation, one could start service without binding to it (Started service)
and then send broadcasts from activity to service, but one would have to implement a broadcast
receiver and that is an overhead.

To play different audios, refer to https://developer.android.com/media/platform/mediaplayer#mpandservices.
Also check some guide - https://codersguidebook.com/how-to-create-an-android-app/play-sounds-music-android-app.

Check https://stackoverflow.com/questions/23771581/service-callback-to-activity-in-android, there
are 3 ways to communicate between activity and service.

TODO: - User should by notified in notification bar that service is running.
      - Ask LLM about errors in implementation
    Example - https://www.youtube.com/watch?v=YZL-_XJSClc&list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm&index=8

2026-09-29
    Asked Kimi on problems in code:
    Answer:
    1) Если в любой из launch-корутин упадёт исключение (а MediaPlayer кидает IllegalStateException
       на раз-два — см. ниже), с SupervisorJob оно уйдёт в глобальный обработчик → краш приложения.
       С обычным Job упавшая команда хотя бы отменила бы скоуп и не дала бы запускать следующие. Для
       сервиса-однозадачника — берите Job или ловите исключения внутри.
    2) Устаревший/несовместимый stopForeground
       stopForeground(STOP_FOREGROUND_REMOVE) // deprecated, на S+ неверный флаг
       Правильно:
       ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
    3) scope.coroutineContext.job.cancel() — хрупко
       Лучше scope.cancel() — тот же эффект, но без магического индексирования. Мелочь, но читаемость.
    4) override fun onStart() {
           super.onStart()
           startForegroundService(intent) // ❌ вызывается КАЖДЫЙ раз при возврате на экран
       }
       Сервис уже запущен → onStartCommand вызовется заново → уведомление пересоберётся. Нужен флаг
       или перенос в onCreate с проверкой:
       override fun onCreate(savedInstanceState: Bundle?) {
           ...
           if (savedInstanceState == null) { // только при первом создании
               startForegroundService(Intent(this, PlayAudioService::class.java))
           }
       }
       Спросил - ты не ошибся? Ответ:
       savedInstanceState == null — да, именно так
       Первый запуск Activity → savedInstanceState == null
       Поворот экрана → != null
       Восстановление после уничтожения процесса → != null
       «Don't keep activities» + возврат со стека → != null
       Так что мой сниппет делает ровно то, что заявлено: сервис стартует только при первом создании
        Activity. Но тут есть нюанс, который стоит держать в голове: если пользователь ушёл со экрана
         и вернулся через back-stack — Activity пересоздаётся с != null, сервис при этом продолжает
         жить (это и есть цель). А если сервис был убит системой, а Activity восстановилась — сервис
          не перезапустится, пока пользователь не выйдет и не зайдёт заново. Для медиасервиса это
          может быть нежелательно — поэтому в проде обычно стартуют сервис не из Activity вообще, а
          из места, где решение принимается осознанно (Application / пользовательское действие).

    Кстати, в 2026 году для нового кода стоит смотреть на MediaSessionService/Media3 ExoPlayer —
    связка MediaPlayer + ручное foreground-сервис-управление считается легаси. Но как учебный пример
    для понимания сервисов — нормальная база.

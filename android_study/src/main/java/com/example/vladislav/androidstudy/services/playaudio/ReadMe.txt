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

TODO: User should by notified in notification bar that service is running.
    Example - https://www.youtube.com/watch?v=YZL-_XJSClc&list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm&index=8

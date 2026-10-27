package com.example.vladislav.androidstudy.compose.tracks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Tracks(
    tracks: List<String>
) {
    var selectedTrack by remember { mutableStateOf("") }
    // mutableStateMapOf триггерит рекомпозицию при изменении элементов списка (likedTracks[track] = ...),
    // в отличие от обычного MutableMap, который Compose не отслеживает.
    // remember(tracks) — инициализируем только при изменении состава треков. Н-р tracks.size не
    // подходит, так как кол-во может остаться тем же, а содержимое изменится.
    val likedTracks = remember(tracks) {
        tracks.map { it to false }.toMutableStateMap()
    }
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        for (track in tracks) {
            if (track == selectedTrack)
                TrackCard(
                    Modifier,
                    trackName = track,
                    liked = likedTracks[track] != false,
                    onLikeClick = {
                        likedTracks[track] = likedTracks[track] == false
                    }
                )
            else
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .padding(8.dp)
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .clickable(
                            // To remove ripple effect, since it's not good looking
                            indication = null,
                            interactionSource = null,
                            onClick = { selectedTrack = track }
                        ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = track,
                    style = MaterialTheme.typography.headlineSmall
                )
        }
    }
}

@Preview
@Composable
fun TracksPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Tracks(
            listOf("Track #1", "Track #2", "Track #3", "Track #4", "Track #5", "Track #6")
        )
    }
}

@Preview(
    name = "Dark Theme",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun TracksPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Tracks(
            listOf("Track #1", "Track #2", "Track #3", "Track #4", "Track #5", "Track #6")
        )
    }
}
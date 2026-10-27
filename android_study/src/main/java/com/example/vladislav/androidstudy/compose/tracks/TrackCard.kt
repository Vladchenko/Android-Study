package com.example.vladislav.androidstudy.compose.tracks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TrackCard(
    modifier: Modifier,
    liked: Boolean,
    trackName: String,
    onLikeClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val buttonColor = remember(liked) {
        if (liked) colorScheme.surfaceVariant.darken(0.7f)
        else colorScheme.surfaceVariant
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    modifier = modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .shadow(
                            8.dp, RoundedCornerShape(8.dp),
                        )
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = "Performer",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp),
                        text = trackName
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .size(ICON_BUTTON_SIZE_DP)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {},
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .size(ICON_BUTTON_SIZE_DP)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {},
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .size(ICON_BUTTON_SIZE_DP)
                        .background(
                            color = buttonColor
                        )
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {}    //TODO Remove
                        ),
                    onClick = onLikeClick,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Like"
                    )
                }
            }
        }
    }
}

fun Color.darken(factor: Float = 0.8f): Color {
    return copy(
        red = (red * factor).coerceAtMost(1f),
        green = (green * factor).coerceAtMost(1f),
        blue = (blue * factor).coerceAtMost(1f)
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun TrackCardNotLikedPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        TrackCard(Modifier, false, "Track #1", {})
    }
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun TrackCardLikedPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        TrackCard(Modifier, true, "Track #1", {})
    }
}

@Preview(
    name = "Dark Theme",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun TrackCardNotLikedDarkPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        TrackCard(Modifier, false, "Track #1", {})
    }
}

@Preview(
    name = "Dark Theme",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun TrackCardLikedDarkPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        TrackCard(Modifier, true, "Track #1", {})
    }
}

private val ICON_BUTTON_SIZE_DP = 40.dp
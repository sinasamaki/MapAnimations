package com.sinasamaki.mapanimations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay

val singapore = LatLng(1.35, 103.87)

val markers = listOf(
    LatLng(1.3858634975481563, 103.8462083414197),
    LatLng(1.4037113262262306, 103.89027737081051),
    LatLng(1.3626502930861797, 103.89601964503527),
    LatLng(1.3622762307127767, 103.87142580002546),
    LatLng(1.3473606140053167, 103.86780682951212),
    LatLng(1.3378772508649863, 103.84277477860451),
    LatLng(1.3207777426217209, 103.85276332497597),
    LatLng(1.2958169088568747, 103.88372272253036),
    LatLng(1.2813376485936374, 103.84258735924959),
    LatLng(1.2745996043392196, 103.88472251594067),
    LatLng(1.2644904937623147, 103.87055341154337),
)

@Composable
fun MapOverlayScope.AnimatedMarkers() {

    markers.forEachIndexed { index, marker ->
        key(index) {
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = index, block = {
                while (true) {
                    delay(200L * index)
                    visible = true
                    delay(2000L)
                    visible = false
                    delay(2000L)
                }
            })

            AnimatedVisibility(
                visible = visible,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { marker.toPx() }
                    .size(20.dp),
                enter = slideInVertically(
                    spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                ) { -it * 2 },
                exit = slideOutVertically(
                ) { -it } + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Red, CircleShape)
                        .size(20.dp)
                )
            }
        }
    }


}
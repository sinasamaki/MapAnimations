package com.sinasamaki.mapanimations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import com.google.android.gms.maps.model.LatLng


val road = listOf(
    LatLng(1.2816104951029417, 103.87605261057615),
    LatLng(1.2844180638825455, 103.87805018574),
    LatLng(1.2882241649126345, 103.87667588889599),
    LatLng(1.293153149472656, 103.8752419129014),
    LatLng(1.2995837755926738, 103.87755028903484),
    LatLng(1.3022666340075706, 103.87805018574),
    LatLng(1.3129977038624894, 103.8748674094677),
    LatLng(1.3197403384872208, 103.875552713871),
    LatLng(1.322423175302462, 103.88098418712616),
    LatLng(1.325043664534547, 103.8842923566699),
    LatLng(1.3301596103840008, 103.88510305434465),
    LatLng(1.3332791824605037, 103.88928562402725),
    LatLng(1.341706398207961, 103.88397954404354),
    LatLng(1.3488186614800077, 103.88035856187344),
)


@Composable
fun MapOverlayScope.AnimatedRoad() {
    val animate = rememberInfiniteTransition()
    val phase = animate.animateFloat(
        initialValue = 1300f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val path = Path()
            road.map {
                it.toPx()
            }.map {
                Pair(it.x.toFloat(), it.y.toFloat())
            }.let {
                path.moveTo(it.first().first, it.first().second)
                it.forEach { (x, y) ->
                    path.lineTo(x, y)
                }
            }
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF28D8D2),
                        Color(0xFF3160EC),
                        Color(0xFFCF0E78),
                        Color(0xFFFDB90D),
                    )
                ),
                style = Stroke(
                    width = 30f,
                    join = StrokeJoin.Round,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(130f, 130f),
                        phase.value,
                    )
                )
            )
        }
    )
}
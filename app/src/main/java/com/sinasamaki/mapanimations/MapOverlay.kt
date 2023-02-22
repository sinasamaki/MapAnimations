package com.sinasamaki.mapanimations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

@Composable
fun MapOverlay(
    cameraPositionState: CameraPositionState,
    content: @Composable MapOverlayScope.() -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        MapOverlayScope(cameraPositionState).content()
    }
}

class MapOverlayScope(
    private val cameraPositionState: CameraPositionState,
) {

    @Stable
    fun Modifier.align(alignment: Alignment) = this.composed {
        var intSize by remember { mutableStateOf(IntSize.Zero) }
        onSizeChanged { intSize = it }.offset {
            alignment.align(intSize, IntSize.Zero, LayoutDirection.Ltr)
        }
    }

    @Stable
    fun LatLng.toPx(): IntOffset {
        cameraPositionState.position
        return cameraPositionState.projection
            ?.toScreenLocation(this)
            ?.let { point ->
                IntOffset(point.x, point.y)
            } ?: IntOffset.Zero
    }
}
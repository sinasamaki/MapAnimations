package com.sinasamaki.mapanimations

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun App() {
    val cameraPositionState = rememberCameraPositionState("camera") {
        position = CameraPosition.fromLatLngZoom(singapore, 13f)
    }

    var currentDemo: MapDemo by remember {
        mutableStateOf(MapDemo.AnimatedMarkers)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("COORDS", "${it}")
            }
        ) {}

        MapOverlay(cameraPositionState = cameraPositionState) {
            when (currentDemo) {
                MapDemo.AnimatedMarkers -> {
                    AnimatedMarkers()
                }

                MapDemo.AnimatedRoad -> {
                    AnimatedRoad()
                }
            }
        }

        var expanded by remember { mutableStateOf(false) }
        Button(
            onClick = { expanded = true },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(text = currentDemo.title)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            allDemos.forEach {
                DropdownMenuItem(
                    onClick = {
                        currentDemo = it
                        expanded = false
                    }) {
                    Text(text = it.title)
                }
            }
        }
    }
}

val allDemos = listOf(
    MapDemo.AnimatedMarkers,
    MapDemo.AnimatedRoad,
)

sealed class MapDemo(val title: String) {
    object AnimatedMarkers : MapDemo("Animated Markers")
    object AnimatedRoad : MapDemo("Animated Road")
}
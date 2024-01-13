package com.filppable_card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun FlippableCard(
    modifier: Modifier = Modifier,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    var isRotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!isRotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateBack by animateFloatAsState(
        targetValue = if (isRotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateColor by animateColorAsState(
        targetValue = if (isRotated) Color.Red else Color.Blue,
        animationSpec = tween(500), label = ""
    )

    var tapCounter by remember {
        mutableIntStateOf(0)
    }

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }


    LaunchedEffect(key1 = tapCounter, block = {
        delay(300)
        tapCounter = 0
    })

    fun reset() {
        isRotated = false
        offsetX = 0f
        offsetY = 0f
    }
    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = modifier

                .fillMaxSize(.5f)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        tapCounter += 1
                        if (tapCounter == 2) {
                            isRotated = !isRotated
                            tapCounter = 0
                        }
                    }
                }
                .pointerInput(Unit) {

                    detectDragGestures(
                        onDragStart = { },
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y

                        },
                        onDragCancel = {},
                        onDragEnd = {

                            if(offsetX > 150) {
                                onConfirm()
                                reset()

                            } else if (offsetX < - 150) {
                                onDismiss()
                                reset()

                            } else {
                                offsetX = 0f
                                offsetY = 0f
                            }

                        }
                    )
                    detectDragGestures { change, dragAmount ->

                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .background(Color.Transparent)
                .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))



        ) {
            Column(
                Modifier
                    .fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if(isRotated) {
                    Box(modifier = Modifier
                        .graphicsLayer {
                            alpha = if (isRotated) animateBack else animateFront
                            rotationY = rotation
                        }) {
                        backContent()
                    }

                } else {
                    Box(modifier = Modifier
                        .graphicsLayer {
                            alpha = if (isRotated) animateBack else animateFront
                            rotationY = rotation
                        }) {
                        frontContent()
                    }

                }
            }
        }
    }
}
package com.wing.tree.bruni.kordle.play.view.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.wing.tree.bruni.kordle.constant.Duration
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OutlinedFlipCard(
    modifier: Modifier,
    index: Int,
    enabled: Boolean,
    side: Side,
    onClick: () -> Unit,
    back: @Composable () -> Unit,
    front: @Composable () -> Unit
) {
    val rotation = animateFloatAsState(
        targetValue = side.angle,
        animationSpec = tween(
            durationMillis = Duration.Flip.DurationMillis,
            delayMillis = Duration.Flip.DelayMillis.times(index),
            easing = FastOutSlowInEasing
        )
    )

    OutlinedCard(
        onClick = onClick,
        modifier = modifier.graphicsLayer {
            rotationY = rotation.value
        },
        enabled = enabled
    ) {
        if (rotation.value <= 90.0F) {
            Box(Modifier.fillMaxSize()) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180.0F
                    },
            ) {
                back()
            }
        }
    }
}
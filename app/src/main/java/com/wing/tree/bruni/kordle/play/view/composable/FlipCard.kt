package com.wing.tree.bruni.kordle.play.view.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FlipCard(
    modifier: Modifier,
    side: Side,
    onClick: () -> Unit,
    back: @Composable () -> Unit,
    front: @Composable () -> Unit
) {
    val rotation = animateFloatAsState(
        targetValue = side.angle,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing,
        )
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
            },
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

internal enum class Side(val angle: Float) {
    Back(180.0F) {
        override val next: Side
            get() = Front
    },
    Front(0.0F) {
        override val next: Side
            get() = Back
    };

    abstract val next: Side
}
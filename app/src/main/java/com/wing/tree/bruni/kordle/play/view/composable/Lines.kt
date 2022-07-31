package com.wing.tree.bruni.kordle.play.view.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.kordle.domain.model.play.Action
import com.wing.tree.bruni.kordle.play.model.Letter
import com.wing.tree.bruni.kordle.play.model.Line
import com.wing.tree.bruni.kordle.play.state.LinesState
import com.wing.tree.bruni.kordle.play.view.PlayFragment
import com.wing.tree.bruni.kordle.play.viewmodel.PlayViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun Lines(
    modifier: Modifier,
    state: LinesState,
    `try`: Int,
    onLetterClickListener: PlayFragment.OnLetterClickListener
) {
    when(state) {
        is LinesState.Loading -> Unit
        is LinesState.Content -> LinesContent(
            modifier = modifier,
            lines = state.lines,
            `try` = `try`,
            onLetterClickListener = onLetterClickListener
        )
        is LinesState.Error -> Unit
    }
}

@Composable
private fun LinesContent(
    modifier: Modifier,
    lines: List<Line>,
    `try`: Int,
    onLetterClickListener: PlayFragment.OnLetterClickListener
) {
    Column(modifier = modifier) {
        lines.forEach { line ->
            val enabled = line.`try` == `try`

            Line(
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                line = line,
                onLetterClickListener = onLetterClickListener
            )
        }
    }
}

@Composable
private fun Line(
    modifier: Modifier,
    enabled: Boolean,
    line: Line,
    onLetterClickListener: PlayFragment.OnLetterClickListener
) {
    val durationMillis = 116

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        line.forEach { letter ->
            val scale = remember { Animatable(1.0F) }

            when(letter.action) {
                Action.Add -> LaunchedEffect(letter, scale) {
                    scale.animateTo(
                        targetValue = 1.15F,
                        animationSpec = tween(
                            durationMillis = durationMillis,
                            easing = LinearEasing
                        )
                    )

                    scale.animateTo(
                        targetValue = 1.0F,
                        animationSpec = tween(
                            durationMillis = durationMillis,
                            delayMillis = durationMillis,
                            easing = LinearEasing
                        )
                    )
                }
                else -> Unit
            }

            Letter(
                modifier = Modifier
                    .size(56.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    },
                enabled = enabled,
                letter = letter,
                submitted = letter.submitted,
                onLetterClickListener = onLetterClickListener
            )
        }
    }
}

@Composable
private fun Letter(
    modifier: Modifier,
    enabled: Boolean,
    letter: Letter,
    submitted: Boolean,
    onLetterClickListener: PlayFragment.OnLetterClickListener
) {
    val side = if (submitted) {
        Side.Back
    } else {
        Side.Front
    }

    val color = when(letter.state) {
        is Letter.State.Undefined -> Color.Black
        is Letter.State.NotIn -> Color.Black
        is Letter.State.In.Mismatched -> Color.Yellow
        is Letter.State.In.Matched -> Color.Green
    }

    OutlinedFlipCard(
        modifier = modifier,
        index = letter.index,
        side = side,
        enabled = enabled,
        onClick = { onLetterClickListener.onLetterClick(letter) },
        back = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color),
                contentAlignment = Alignment.Center
            ) {
                Text(text = letter.letter)
            }
        },
        front = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = letter.letter)
            }
        }
    )
}
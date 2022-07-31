package com.wing.tree.bruni.kordle.play.view.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.kordle.R
import com.wing.tree.bruni.kordle.domain.constant.*
import com.wing.tree.bruni.kordle.play.model.Key
import com.wing.tree.bruni.kordle.play.model.Keyboard
import com.wing.tree.bruni.kordle.play.state.KeyboardState

@Composable
internal fun Keyboard(
    modifier: Modifier,
    state: KeyboardState,
    onLetterClick: (Key.Letter) -> Unit,
    onReturnClick: () -> Unit
) {
    when(state) {
        is KeyboardState.Loading -> Unit
        is KeyboardState.Content -> KeyboardContent(
            modifier = modifier,
            keyboard = state.keyboard,
            onLetterClick = onLetterClick,
            onReturnClick = onReturnClick
        )
        is KeyboardState.Error -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KeyboardContent(
    modifier: Modifier,
    keyboard: Keyboard,
    onLetterClick: (Key.Letter) -> Unit,
    onReturnClick: () -> Unit
) {
    val size = 32.dp
    Column(modifier = modifier) {
        with(keyboard.letters) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                get(ONE)?.forEach {
                    Letter(
                        modifier = Modifier.size(size),
                        letter = it,
                        onClick = onLetterClick
                    )
                }

                Spacer(modifier = Modifier.width(size))

                get(TWO)?.forEach {
                    Letter(
                        modifier = Modifier.size(size),
                        letter = it,
                        onClick = onLetterClick
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                get(THREE)?.forEach {
                    Letter(
                        modifier = Modifier.size(size),
                        letter = it,
                        onClick = onLetterClick
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                get(FOUR)?.forEach {
                    Letter(
                        modifier = Modifier.size(size),
                        letter = it,
                        onClick = onLetterClick
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                get(FIVE)?.forEach {
                    Letter(
                        modifier = Modifier.size(size ),
                        letter = it,
                        onClick = onLetterClick
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = onReturnClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_keyboard_return_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun Letter(
    modifier: Modifier,
    letter: Key.Letter,
    onClick: (Key.Letter) -> Unit
) {
    FlipCard(
        modifier = modifier,
        side = Side.Front,
        onClick = { onClick(letter) },
        back = {
            Box(
                modifier = Modifier.fillMaxSize(),
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
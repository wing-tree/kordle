package com.wing.tree.bruni.kordle.play.state

import com.wing.tree.bruni.kordle.play.model.Keyboard
import com.wing.tree.bruni.kordle.play.model.Line

data class PlayState(
    val progressState: ProgressState,
    val resultState: ResultState
)

data class ProgressState(
    val linesState : LinesState,
    val keyboardState : KeyboardState,
    val `try`: Int
)

sealed interface LinesState {
    object Loading: LinesState
    data class Content(val lines: List<Line>) : LinesState
    data class Error(val throwable: Throwable) : LinesState
}

sealed interface KeyboardState {
    object Loading : KeyboardState
    data class Content(val keyboard: Keyboard) : KeyboardState
    data class Error(val throwable: Throwable) : KeyboardState
}

sealed interface ResultState {
    object Loading : ResultState
    object Content : ResultState
    data class Error(val throwable: Throwable) : ResultState
}
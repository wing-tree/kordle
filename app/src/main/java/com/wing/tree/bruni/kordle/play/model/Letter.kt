package com.wing.tree.bruni.kordle.play.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wing.tree.bruni.kordle.domain.model.play.Action

data class Letter(
    val action: Action,
    val index: Int,
    val letter: String
) {
    val isBlank: Boolean get() = letter.isBlank()
    val isNotBlank: Boolean get() = isBlank.not()

    var submitted by mutableStateOf(false)
    var state: State by mutableStateOf(State.Undefined())

    sealed class State {
        abstract val priority: Int

        data class Undefined(
            override val priority: Int = Priority.UNDEFINED
        ): State()

        data class NotIn(
            override val priority: Int = Priority.NOT_IN
        ): State()

        sealed class In : State() {
            data class Mismatched(
                override val priority: Int = Priority.MISMATCHED
            ) : In()

            data class Matched(
                override val priority: Int = Priority.MATCHED
            ) : In()
        }

        private object Priority {
            const val UNDEFINED = 0
            const val NOT_IN = 1
            const val MISMATCHED = 5
            const val MATCHED = 25
        }
    }
}
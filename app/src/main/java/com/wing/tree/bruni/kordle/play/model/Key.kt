package com.wing.tree.bruni.kordle.play.model

sealed class Key {
    data class Letter(val letter: String): Key()

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

package com.wing.tree.bruni.kordle.play.model

import androidx.compose.runtime.Stable
import com.wing.tree.bruni.kordle.domain.constant.EMPTY
import com.wing.tree.bruni.kordle.domain.constant.WORD_LENGTH
import com.wing.tree.bruni.kordle.domain.model.play.Action
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class Line(
    val letters: ImmutableList<Letter> = List(WORD_LENGTH) {
        Letter(action = Action.Initialize, index = it, letter = EMPTY)
    }.toImmutableList(),
    val `try`: Int
) : Iterable<Letter> {
    val isNotEmpty: Boolean get() = letters.any { it.isNotBlank }
    val isNotFilled: Boolean get() = letters.any { it.isBlank }
    val jamo: String get() = letters.joinToString(EMPTY) { it.letter }

    fun allMatched(): Boolean = all { it.state is Letter.State.In.Matched }
    fun undefined() = filter { it.state is Letter.State.Undefined }

    operator fun get(index: Int) = letters[index]

    override fun iterator(): Iterator<Letter> {
        return object : Iterator<Letter> {
            private var index = 0

            override fun hasNext(): Boolean {
                return index <= letters.lastIndex
            }

            override fun next(): Letter {
                return letters[index++]
            }
        }
    }

    companion object {
        fun Line.add(letter: String): Line? {
            if (isNotFilled) {
                val index = letters.indexOfFirst { it.isBlank }

                require(index in 0 until WORD_LENGTH)

                val immutableList = letters
                    .toMutableList()
                    .apply {
                        set(index, Letter(action = Action.Add, index = index, letter = letter))
                    }
                    .toImmutableList()

                return Line(immutableList, `try`)
            } else {
                return null
            }
        }

        fun Line.removeAt(index: Int): Line {
            require(index in 0 until WORD_LENGTH)

            val immutableList = letters
                .toMutableList()
                .apply {
                    if (isNotEmpty) {
                        val letter = get(index)

                        if (letter.submitted.not()) {
                            set(index, letter.copy(action = Action.Remove, letter = EMPTY))
                        }
                    }
                }
                .toImmutableList()

            return Line(immutableList, `try`)
        }

        fun Line.removeLast(): Line {
            val immutableList = letters
                .toMutableList()
                .apply {
                    if (isNotEmpty) {
                        findLast { it.isNotBlank }?.let { letter ->
                            if (letter.submitted.not()) {
                                set(
                                    index = letter.index,
                                    element = letter.copy(
                                        action = Action.Remove,
                                        letter = EMPTY
                                    )
                                )
                            }
                        }
                    }
                }
                .toImmutableList()

            return Line(immutableList, `try`)
        }
    }
}
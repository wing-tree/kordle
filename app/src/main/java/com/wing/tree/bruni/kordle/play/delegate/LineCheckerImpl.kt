package com.wing.tree.bruni.kordle.play.delegate

import com.wing.tree.bruni.kordle.domain.constant.EMPTY
import com.wing.tree.bruni.kordle.domain.usecase.core.getOrDefault
import com.wing.tree.bruni.kordle.domain.usecase.play.ContainsJamoUseCase
import com.wing.tree.bruni.kordle.exception.JamoNotContainsException
import com.wing.tree.bruni.kordle.play.model.Letter
import com.wing.tree.bruni.kordle.play.model.Line
import kotlinx.collections.immutable.toImmutableList

class LineCheckerImpl(private val containsJamoUseCase: ContainsJamoUseCase) : LineChecker {
    private var jamo = EMPTY

    override suspend fun checkLine(jamo: String, line: Line): Result<Line> {
        this.jamo = jamo

        val containsJamo = containsJamoUseCase(ContainsJamoUseCase.Parameter(line.jamo))
            .getOrDefault(false)

        if (containsJamo.not()) {
            return Result.failure(JamoNotContainsException)
        }

        checkMatchedLetters(line)
        checkMismatchedLetters(line)
        checkNotInLetters(line)

        return Result.success(line.copy(letters = line.letters.map { it.copy() }.toImmutableList()))
    }

    private fun checkMatchedLetters(line: Line) {
        val zip = jamo zip line.jamo

        zip.forEachIndexed { index, (first, second) ->
            if (first == second) {
                jamo = jamo.replaceFirst("$first", EMPTY)

                line[index].submitted = true
                line[index].state = Letter.State.In.Matched()
            }
        }
    }

    private fun checkMismatchedLetters(line: Line) {
        line.undefined().forEach { letter ->
            if (letter.letter in jamo) {
                jamo = jamo.replaceFirst(letter.letter, EMPTY)

                letter.submitted = true
                letter.state = Letter.State.In.Mismatched()
            }
        }
    }

    private fun checkNotInLetters(line: Line) {
        line.undefined().forEach {

            it.submitted = true
            it.state = Letter.State.NotIn()
        }
    }
}
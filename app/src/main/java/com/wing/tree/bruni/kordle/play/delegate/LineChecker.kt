package com.wing.tree.bruni.kordle.play.delegate

import com.wing.tree.bruni.kordle.play.model.Line

interface LineChecker {
    suspend fun checkLine(jamo: String, line: Line): Result<Line>
}
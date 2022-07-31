package com.wing.tree.bruni.kordle.extension

import com.wing.tree.bruni.kordle.play.model.Line
import kotlinx.coroutines.flow.MutableStateFlow

internal inline fun MutableStateFlow<List<Line>>.setValue(block: (MutableList<Line>) -> List<Line>) {
    value = block(value.toMutableList())
}

internal inline fun MutableStateFlow<List<Line>>.setValueAfter(block: MutableList<Line>.() -> Unit) {
    value.toMutableList().apply {
        block(this)
        value = this
    }
}
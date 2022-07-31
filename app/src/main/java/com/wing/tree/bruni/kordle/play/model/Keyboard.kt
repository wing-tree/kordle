package com.wing.tree.bruni.kordle.play.model

import com.wing.tree.bruni.kordle.domain.constant.*
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

data class Keyboard(val letters: ImmutableMap<Int, List<Key.Letter>> = toImmutableMap()) {
    companion object {
        val letters = hashMapOf(
            ONE to listOf("ㅃ", "ㅉ", "ㄸ", "ㄲ", "ㅆ"),
            TWO to listOf("ㅒ", "ㅖ"),
            THREE to listOf("ㅂ", "ㅈ", "ㄷ", "ㄱ", "ㅅ", "ㅗ", "ㅐ", "ㅔ"),
            FOUR to listOf("ㅁ", "ㄴ", "ㅇ", "ㄹ", "ㅎ", "ㅓ", "ㅏ", "ㅣ"),
            FIVE to listOf("ㅋ", "ㅌ", "ㅊ", "ㅠ", "ㅜ", "ㅡ")
        )

        private fun toImmutableMap(): ImmutableMap<Int, List<Key.Letter>> {
            return letters.entries.associate {
                it.key to it.value.map { letter -> Key.Letter(letter) }
            }.toImmutableMap()
        }
    }
}
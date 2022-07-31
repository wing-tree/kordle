package com.wing.tree.bruni.kordle.play.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.kordle.constant.Duration
import com.wing.tree.bruni.kordle.domain.constant.DEFAULT_MAXIMUM_TRY
import com.wing.tree.bruni.kordle.domain.constant.WORD_LENGTH
import com.wing.tree.bruni.kordle.domain.constant.ZERO
import com.wing.tree.bruni.kordle.domain.extension.long
import com.wing.tree.bruni.kordle.domain.usecase.core.getOrNull
import com.wing.tree.bruni.kordle.domain.usecase.play.ContainsJamoUseCase
import com.wing.tree.bruni.kordle.domain.usecase.play.RandomKoreanVocabularyUseCase
import com.wing.tree.bruni.kordle.extension.setValueAfter
import com.wing.tree.bruni.kordle.play.delegate.LineChecker
import com.wing.tree.bruni.kordle.play.delegate.LineCheckerImpl
import com.wing.tree.bruni.kordle.play.model.Key
import com.wing.tree.bruni.kordle.play.model.Keyboard
import com.wing.tree.bruni.kordle.play.model.Line
import com.wing.tree.bruni.kordle.play.model.Line.Companion.add
import com.wing.tree.bruni.kordle.play.model.Line.Companion.removeAt
import com.wing.tree.bruni.kordle.play.model.Line.Companion.removeLast
import com.wing.tree.bruni.kordle.play.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    containsJamoUseCase: ContainsJamoUseCase,
    randomKoreanVocabularyUseCase: RandomKoreanVocabularyUseCase,
    application: Application
) : AndroidViewModel(application), LineChecker by LineCheckerImpl(containsJamoUseCase) {
    private val ioDispatcher = Dispatchers.IO

    private lateinit var jamo: String

    private val lines = MutableStateFlow(List(DEFAULT_MAXIMUM_TRY) { Line(`try` = it) })
    private val keyboard = MutableStateFlow(Keyboard())
    private val `try` = MutableStateFlow(ZERO)

    private val resultState = MutableStateFlow<ResultState>(ResultState.Loading)

    private val triesExceeded: Boolean get() = DEFAULT_MAXIMUM_TRY.dec() < `try`.value

    val playState: StateFlow<PlayState> = combine(
        lines,
        keyboard,
        `try`,
        resultState
    ) { lines, keyboard, `try`, resultState ->
        PlayState(
            progressState = ProgressState(
                linesState = LinesState.Content(lines = lines),
                keyboardState = KeyboardState.Content(keyboard = keyboard),
                `try` = `try`
            ),
            resultState = resultState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PlayState(
            progressState = ProgressState(
                linesState = LinesState.Loading,
                keyboardState = KeyboardState.Loading,
                `try` = `try`.value
            ),
            resultState = ResultState.Loading
        )
    )

    init {
        viewModelScope.launch {
            val result = randomKoreanVocabularyUseCase().getOrNull()!!
            jamo = result.jamo
        }
    }

    fun add(letter: Key.Letter) {
        if (triesExceeded) {
            return
        }

        val `try` = `try`.value

        lines.setValueAfter {
            get(`try`).add(letter.letter)?.let {
                set(`try`, it)
            }
        }
    }

    fun removeAt(index: Int) {
        val `try` = `try`.value

        lines.setValueAfter {
            set(`try`, get(`try`).removeAt(index))
        }
    }


    fun removeLast() {
        val `try` = `try`.value

        lines.setValueAfter {
            set(`try`, get(`try`).removeLast())
        }
    }

    fun submit() {
        if (triesExceeded) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            val `try` = `try`.value

            with(checkLine(jamo, lines.value[`try`])) {
                onFailure {

                }.onSuccess { line ->
                    lines.setValueAfter {
                        set(`try`, line)

                        val timeMillis = Duration.Flip.DelayMillis
                            .times(WORD_LENGTH)
                            .plus(Duration.Flip.DurationMillis)
                            .long

                        delay(timeMillis)

                        incrementTry()

                        when {
                            line.allMatched() -> Unit
                            triesExceeded -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun incrementTry() {
        `try`.value = `try`.value.inc()
    }
}
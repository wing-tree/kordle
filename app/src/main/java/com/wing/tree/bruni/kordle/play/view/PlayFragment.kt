package com.wing.tree.bruni.kordle.play.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wing.tree.bruni.kordle.play.model.Letter
import com.wing.tree.bruni.kordle.play.view.composable.Keyboard
import com.wing.tree.bruni.kordle.play.view.composable.Lines
import com.wing.tree.bruni.kordle.play.viewmodel.PlayViewModel
import com.wing.tree.bruni.kordle.ui.theme.KordleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayFragment : Fragment() {
    private val viewModel by viewModels<PlayViewModel>()

    fun interface OnLetterClickListener {
        fun onLetterClick(letter: Letter)
    }

    private val onLetterClickListener = OnLetterClickListener {
        viewModel.removeAt(it.index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                KordleTheme {
                    val state by viewModel.playState.collectAsState()
                    val progressState = state.progressState
                    val linesState = progressState.linesState
                    val keyboardState = progressState.keyboardState

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Lines(
                                modifier = Modifier.weight(1.0F),
                                state = linesState,
                                `try` = progressState.`try`,
                                onLetterClickListener = onLetterClickListener
                            )

                            Keyboard(
                                modifier = Modifier.weight(1.0F),
                                state = keyboardState,
                                onLetterClick = {
                                    viewModel.add(it)
                                },
                                onReturnClick = {
                                    viewModel.submit()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
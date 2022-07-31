package com.wing.tree.bruni.kordle.domain.usecase.play

import com.wing.tree.bruni.kordle.domain.model.KoreanVocabulary
import com.wing.tree.bruni.kordle.domain.repository.KoreanVocabularyRepository
import com.wing.tree.bruni.kordle.domain.usecase.core.IOCoroutineDispatcher
import com.wing.tree.bruni.kordle.domain.usecase.core.NoParameterCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RandomKoreanVocabularyUseCase @Inject constructor(
    private val repository: KoreanVocabularyRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : NoParameterCoroutineUseCase<KoreanVocabulary>(coroutineDispatcher) {
    override suspend fun execute(): KoreanVocabulary {
        return repository.random()
    }
}
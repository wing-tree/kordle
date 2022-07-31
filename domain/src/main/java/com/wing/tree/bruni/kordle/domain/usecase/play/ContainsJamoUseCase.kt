package com.wing.tree.bruni.kordle.domain.usecase.play

import com.wing.tree.bruni.kordle.domain.extension.notNull
import com.wing.tree.bruni.kordle.domain.repository.KoreanVocabularyRepository
import com.wing.tree.bruni.kordle.domain.usecase.core.CoroutineUseCase
import com.wing.tree.bruni.kordle.domain.usecase.core.IOCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ContainsJamoUseCase @Inject constructor(
    private val repository: KoreanVocabularyRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<ContainsJamoUseCase.Parameter, Boolean>(coroutineDispatcher) {
    override suspend fun execute(parameter: Parameter): Boolean {
        return repository.findByJamo(parameter.jamo).notNull
    }

    data class Parameter(val jamo: String)
}
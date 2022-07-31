package com.wing.tree.bruni.kordle.data.repository

import com.wing.tree.bruni.kordle.data.datasource.KoreanVocabularyDataSource
import com.wing.tree.bruni.kordle.domain.model.KoreanVocabulary
import com.wing.tree.bruni.kordle.domain.repository.KoreanVocabularyRepository
import javax.inject.Inject

class KoreanVocabularyRepositoryImpl @Inject constructor(
    private val dataSource: KoreanVocabularyDataSource
): KoreanVocabularyRepository {
    override suspend fun findByJamo(jamo: String): KoreanVocabulary? {
        return dataSource.findByJamo(jamo)
    }

    override suspend fun random(): KoreanVocabulary {
        return dataSource.random()
    }
}
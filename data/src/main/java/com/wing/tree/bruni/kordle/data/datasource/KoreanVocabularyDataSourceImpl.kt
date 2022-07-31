package com.wing.tree.bruni.kordle.data.datasource

import com.wing.tree.bruni.kordle.data.database.Database
import com.wing.tree.bruni.kordle.data.entity.KoreanVocabulary
import javax.inject.Inject

class KoreanVocabularyDataSourceImpl @Inject constructor(database: Database) : KoreanVocabularyDataSource {
    private val koreanVocabularyDao = database.koreanVocabularyDao()

    override suspend fun findByJamo(jamo: String): KoreanVocabulary? {
        return koreanVocabularyDao.findByJamo(jamo)
    }

    override suspend fun random(): KoreanVocabulary {
        return koreanVocabularyDao.random()
    }
}
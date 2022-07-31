package com.wing.tree.bruni.kordle.data.datasource

import com.wing.tree.bruni.kordle.data.entity.KoreanVocabulary

interface KoreanVocabularyDataSource {
    suspend fun findByJamo(jamo: String): KoreanVocabulary?
    suspend fun random(): KoreanVocabulary
}
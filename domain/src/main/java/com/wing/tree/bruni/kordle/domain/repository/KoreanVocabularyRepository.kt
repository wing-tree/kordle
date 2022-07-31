package com.wing.tree.bruni.kordle.domain.repository

import com.wing.tree.bruni.kordle.domain.model.KoreanVocabulary

interface KoreanVocabularyRepository {
    suspend fun findByJamo(jamo: String): KoreanVocabulary?
    suspend fun random(): KoreanVocabulary
}
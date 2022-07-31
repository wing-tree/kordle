package com.wing.tree.bruni.kordle.data.dao

import androidx.room.*
import com.wing.tree.bruni.kordle.data.entity.KoreanVocabulary
import com.wing.tree.bruni.kordle.domain.constant.ONE

@Dao
interface KoreanVocabularyDao {
    // 고독, 보석, 멍게, 해삼, 가난, 가동, 가공, 목사

    @Query("SELECT * FROM korean_vocabulary ORDER BY RANDOM() LIMIT $ONE")
    suspend fun random(): KoreanVocabulary

    @Query("SELECT * FROM korean_vocabulary WHERE jamo LIKE :jamo LIMIT $ONE")
    suspend fun findByJamo(jamo: String): KoreanVocabulary?
}
package com.wing.tree.bruni.kordle.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wing.tree.bruni.kordle.domain.model.KoreanVocabulary

@Entity(tableName = "korean_vocabulary")
data class KoreanVocabulary(
    @PrimaryKey(autoGenerate = false)
    override val word: String,
    override val jamo: String
) : KoreanVocabulary()
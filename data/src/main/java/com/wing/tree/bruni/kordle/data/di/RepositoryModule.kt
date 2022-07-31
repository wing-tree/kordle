package com.wing.tree.bruni.kordle.data.di

import com.wing.tree.bruni.kordle.data.repository.KoreanVocabularyRepositoryImpl
import com.wing.tree.bruni.kordle.domain.repository.KoreanVocabularyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsKoreanVocabularyRepository(repository: KoreanVocabularyRepositoryImpl): KoreanVocabularyRepository
}
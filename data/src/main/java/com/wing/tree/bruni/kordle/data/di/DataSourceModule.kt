package com.wing.tree.bruni.kordle.data.di

import com.wing.tree.bruni.kordle.data.datasource.KoreanVocabularyDataSource
import com.wing.tree.bruni.kordle.data.datasource.KoreanVocabularyDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsKoreanVocabularyDataSource(dataSource: KoreanVocabularyDataSourceImpl): KoreanVocabularyDataSource
}
package com.wing.tree.bruni.kordle.data.di

import com.wing.tree.bruni.kordle.domain.usecase.core.DefaultCoroutineDispatcher
import com.wing.tree.bruni.kordle.domain.usecase.core.IOCoroutineDispatcher
import com.wing.tree.bruni.kordle.domain.usecase.core.MainCoroutineDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
internal object CoroutineDispatcherModule {
    @DefaultCoroutineDispatcher
    @Provides
    fun providesDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IOCoroutineDispatcher
    @Provides
    fun providesIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainCoroutineDispatcher
    @Provides
    fun providesMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

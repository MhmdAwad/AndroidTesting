package com.mhmdawad.androidtestingplayground.di

import android.content.Context
import androidx.room.Room
import com.mhmdawad.androidtestingplayground.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppTestModule {

    @Provides
    @Named("db_test")
    fun provideRoomDatabaseInMemory(
        @ApplicationContext context: Context
    ): ShoppingDatabase = Room.inMemoryDatabaseBuilder(
        context, ShoppingDatabase::class.java
    ).allowMainThreadQueries().build()
}
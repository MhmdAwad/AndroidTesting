package com.mhmdawad.androidtestingplayground.di

import android.content.Context
import androidx.room.Room
import com.mhmdawad.androidtestingplayground.common.Constants
import com.mhmdawad.androidtestingplayground.data.local.ShoppingDao
import com.mhmdawad.androidtestingplayground.data.local.ShoppingDatabase
import com.mhmdawad.androidtestingplayground.data.remote.PixabayAPI
import com.mhmdawad.androidtestingplayground.data.repository.ShoppingRepositoryImpl
import com.mhmdawad.androidtestingplayground.domain.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        shoppingDatabase: ShoppingDatabase
    ) = shoppingDatabase.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayAPI() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixabayAPI::class.java)

    @Singleton
    @Provides
    fun provideShoppingRepository(
        shoppingDao: ShoppingDao,
        pixabayAPI: PixabayAPI
    ): ShoppingRepository =
        ShoppingRepositoryImpl(shoppingDao, pixabayAPI)
}
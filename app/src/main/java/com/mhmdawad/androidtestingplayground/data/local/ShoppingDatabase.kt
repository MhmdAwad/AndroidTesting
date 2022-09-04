package com.mhmdawad.androidtestingplayground.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity


@Database(
    entities = [ShoppingItemEntity::class],
    version = 1
)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

}


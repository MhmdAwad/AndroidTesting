package com.mhmdawad.androidtestingplayground.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity


interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Delete
    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Query("SELECT * FROM shopping_table")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItemEntity>>

    @Query("SELECT SUM(price * amount) FROM shopping_table")
    fun observeTotalPrice(): LiveData<Float>

}
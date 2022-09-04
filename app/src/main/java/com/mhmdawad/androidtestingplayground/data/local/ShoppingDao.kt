package com.mhmdawad.androidtestingplayground.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity

@Dao
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
package com.mhmdawad.androidtestingplayground.domain.repository

import androidx.lifecycle.LiveData
import com.mhmdawad.androidtestingplayground.common.Resource
import com.mhmdawad.androidtestingplayground.data.remote.responses.ImageResponse
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity)
    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)
    fun observeAllShoppingItems(): LiveData<List<ShoppingItemEntity>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchForImage(searchQuery: String): Resource<ImageResponse>
}
package com.mhmdawad.androidtestingplayground.data.repository

import androidx.lifecycle.LiveData
import com.mhmdawad.androidtestingplayground.common.Resource
import com.mhmdawad.androidtestingplayground.data.local.ShoppingDao
import com.mhmdawad.androidtestingplayground.data.remote.PixabayAPI
import com.mhmdawad.androidtestingplayground.data.remote.responses.ImageResponse
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.domain.repository.ShoppingRepository
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingDao.insertShoppingItem(shoppingItemEntity)
    }

    override suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingDao.deleteShoppingItem(shoppingItemEntity)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItemEntity>> =
        shoppingDao.observeAllShoppingItems()

    override fun observeTotalPrice(): LiveData<Float> =
        shoppingDao.observeTotalPrice()

    override suspend fun searchForImage(searchQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(searchQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.Success(it)
                }?: Resource.Error("")
            }else{
                response.errorBody()?.let {
                    return@let Resource.Error(it.toString()?: "")
                }?: Resource.Error("")
            }
        }catch (e: Exception){
            Resource.Error(e.message?: "")
        }

    }


}
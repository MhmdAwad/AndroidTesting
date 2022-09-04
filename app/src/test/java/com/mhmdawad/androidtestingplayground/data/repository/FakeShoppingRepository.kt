package com.mhmdawad.androidtestingplayground.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mhmdawad.androidtestingplayground.common.Resource
import com.mhmdawad.androidtestingplayground.data.remote.responses.ImageResponse
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.domain.repository.ShoppingRepository


class FakeShoppingRepository: ShoppingRepository {
    private val shoppingList = mutableListOf<ShoppingItemEntity>()
    private val observeAllShoppingItems = MutableLiveData<List<ShoppingItemEntity>>(shoppingList)
    private val observeTotalPrice = MutableLiveData<Float>()


    private fun refreshLiveData(){
        observeAllShoppingItems.postValue(shoppingList)
        observeTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingList.sumOf {
            (it.price * it.amount).toDouble()
        }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingList.add(shoppingItemEntity)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingList.remove(shoppingItemEntity)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItemEntity>> = observeAllShoppingItems

    override fun observeTotalPrice(): LiveData<Float>  = observeTotalPrice

    override suspend fun searchForImage(searchQuery: String): Resource<ImageResponse> {
        return if(searchQuery.isBlank()){
            Resource.Error("Specify a search Text")
        }else{
            Resource.Success(ImageResponse(emptyList(), 0, 0 ))
        }
    }
}
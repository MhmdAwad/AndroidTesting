package com.mhmdawad.androidtestingplayground.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.androidtestingplayground.common.Event
import com.mhmdawad.androidtestingplayground.common.Resource
import com.mhmdawad.androidtestingplayground.data.remote.responses.ImageResponse
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.domain.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository
): ViewModel() {

    val totalPrice = shoppingRepository.observeTotalPrice()
    val shoppingList = shoppingRepository.observeAllShoppingItems()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _currentSelectedImage = MutableLiveData<String>()
    val currentSelectedImage: LiveData<String> = _currentSelectedImage
    fun selectNewImage(imageUrl: String){
        _currentSelectedImage.postValue(imageUrl)
    }

    fun insertShoppingItemIntoDb(shoppingItemEntity: ShoppingItemEntity) = viewModelScope.launch{
        shoppingRepository.insertShoppingItem(shoppingItemEntity)
    }

    fun deleteShoppingItemFromDb(shoppingItemEntity: ShoppingItemEntity) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(shoppingItemEntity)
    }

    fun searchForImage(searchQuery: String){

    }

}
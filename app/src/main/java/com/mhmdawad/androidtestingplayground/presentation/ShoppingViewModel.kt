package com.mhmdawad.androidtestingplayground.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.androidtestingplayground.common.Constants
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

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItemEntity>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItemEntity>>> = _insertShoppingItemStatus

    private val _currentSelectedImage = MutableLiveData<String>()
    val currentSelectedImage: LiveData<String> = _currentSelectedImage
    fun setCurrentImage(imageUrl: String){
        _currentSelectedImage.postValue(imageUrl)
    }

    private fun insertShoppingItemIntoDb(shoppingItemEntity: ShoppingItemEntity) = viewModelScope.launch{
        shoppingRepository.insertShoppingItem(shoppingItemEntity)
    }

    fun deleteShoppingItemFromDb(shoppingItemEntity: ShoppingItemEntity) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(shoppingItemEntity)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String){
        if(name.isBlank() || amountString.isBlank() || priceString.isBlank()){
            _insertShoppingItemStatus.value = Event(Resource.Error("Empty Field!"))
            return
        }

        if(name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.value = Event(Resource.Error("Max Name Exceeded!"))
            return
        }
        if(priceString.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.value = Event(Resource.Error("Max Price Exceeded!"))
            return
        }
        val amount = try{
            amountString.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.value = Event(Resource.Error("Add a valid amount!"))
            return
        }

        val price = try{
            priceString.toFloat()
        }catch (e: Exception){
            _insertShoppingItemStatus.value = Event(Resource.Error("Add a valid price!"))
            return
        }

        val shoppingItem = ShoppingItemEntity(name,amount, price,_currentSelectedImage.value?:"")
        insertShoppingItemIntoDb(shoppingItem)
        setCurrentImage("")
        _insertShoppingItemStatus.value = Event(Resource.Success(shoppingItem))
    }


    fun searchForImage(searchQuery: String){
        if(searchQuery.isBlank())
            return
        _images.value = Event(Resource.Loading())
        viewModelScope.launch {
            val response = shoppingRepository.searchForImage(searchQuery)
            _images.value = Event(response)
        }
    }

}
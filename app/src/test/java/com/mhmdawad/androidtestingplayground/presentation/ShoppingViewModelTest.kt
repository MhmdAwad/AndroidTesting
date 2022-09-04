package com.mhmdawad.androidtestingplayground.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mhmdawad.androidtestingplayground.MainCoroutineRule
import com.mhmdawad.androidtestingplayground.common.Constants
import com.mhmdawad.androidtestingplayground.common.Resource
import com.mhmdawad.androidtestingplayground.data.repository.FakeShoppingRepository
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var shoppingViewModel: ShoppingViewModel

    @Before
    fun setup() {
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty name field_return error`() {
        shoppingViewModel.insertShoppingItem("", "23", "44")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with empty amount field_return error`() {
        shoppingViewModel.insertShoppingItem("hi", "", "44")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with empty price field_return error`() {
        shoppingViewModel.insertShoppingItem("hi", "12", "")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with max name field_return error`() {
        val name = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(name, "12", "12")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with max price field_return error`() {
        val price = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem("sad", "12", price)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with non valid price field_return error`() {
        shoppingViewModel.insertShoppingItem("sad", "12", "price")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with non valid amount field_return error`() {
        shoppingViewModel.insertShoppingItem("sad", "amount", "12")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `insert shopping item with valid fields input_return success`() {
        shoppingViewModel.insertShoppingItem("sad", "2", "12")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()).isNotNull()
        assertThat(value.peekContent()).isInstanceOf(Resource.Success::class.java)
    }
}
package com.mhmdawad.androidtestingplayground.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.mhmdawad.androidtestingplayground.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import com.mhmdawad.androidtestingplayground.R
import com.mhmdawad.androidtestingplayground.common.FragmentFactory
import com.mhmdawad.androidtestingplayground.data.repository.FakeShoppingRepository
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.getOrAwaitValue
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickShoppingImageView_navigateToImagePickFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())
        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )

    }

    @Test
    fun clickAddShoppingItemButton_ShoppingItemInsertedIntoDb(){
        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            shoppingViewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Shopping Name"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("12"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("50"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        val item = ShoppingItemEntity("Shopping Name", 12, 50f,"")

        assertThat(testViewModel.insertShoppingItemStatus.getOrAwaitValue().getContentIfNotHandled()).isNotNull()
        assertThat(testViewModel.insertShoppingItemStatus.getOrAwaitValue().peekContent().data).isEqualTo(
            item
        )
        assertThat(testViewModel.shoppingList.getOrAwaitValue()).contains(item)
    }
}
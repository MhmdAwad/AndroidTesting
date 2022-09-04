package com.mhmdawad.androidtestingplayground.data.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.getOrAwaitValue
import com.mhmdawad.androidtestingplayground.launchFragmentInHiltContainer
import com.mhmdawad.androidtestingplayground.presentation.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    @Named("db_test")
    lateinit var database: ShoppingDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup(){
        hiltAndroidRule.inject()
        shoppingDao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun testingME(){
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Test
    fun insertShoppingItem() = runBlocking{
        val shoppingEntity = ShoppingItemEntity("name", 12, 2f, "image", 1)
        shoppingDao.insertShoppingItem(shoppingEntity)
        val shoppingList = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(shoppingList).contains(shoppingEntity)
    }
//    @Delete
//    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Test
    fun deleteShoppingItem() = runBlocking{
        val shoppingEntity = ShoppingItemEntity("name", 12, 2f, "image", 1)
        shoppingDao.insertShoppingItem(shoppingEntity)
        shoppingDao.deleteShoppingItem(shoppingEntity)
        val shoppingList = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(shoppingList).doesNotContain(shoppingEntity)
    }

//    @Query("SELECT * FROM shopping_table")
//    fun observeAllShoppingItems(): LiveData<List<ShoppingItemEntity>>
//
//    @Query("SELECT SUM(price * amount) FROM shopping_table")
//    fun observeTotalPrice(): LiveData<Float>

    @Test
    fun observeTotalPrice() = runBlocking{
        val shoppingEntity1 = ShoppingItemEntity("name", 12, 2f, "image")
        val shoppingEntity2 = ShoppingItemEntity("name", 12, 2f, "image")
        val shoppingEntity3 = ShoppingItemEntity("name", 12, 2f, "image")
        shoppingDao.insertShoppingItem(shoppingEntity1)
        shoppingDao.insertShoppingItem(shoppingEntity2)
        shoppingDao.insertShoppingItem(shoppingEntity3)
        val shoppingList = shoppingDao.observeTotalPrice().getOrAwaitValue()
        assertThat(shoppingList).isEqualTo((12 * 2) * 3)
    }
}
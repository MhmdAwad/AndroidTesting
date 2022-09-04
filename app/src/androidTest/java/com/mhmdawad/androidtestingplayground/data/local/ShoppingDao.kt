package com.mhmdawad.androidtestingplayground.data.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mhmdawad.androidtestingplayground.domain.model.ShoppingItemEntity
import com.mhmdawad.androidtestingplayground.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()
        shoppingDao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
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
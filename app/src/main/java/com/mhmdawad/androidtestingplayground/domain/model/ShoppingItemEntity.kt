package com.mhmdawad.androidtestingplayground.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "shopping_table"
)
data class ShoppingItemEntity(
    val name: String,
    val amount: Int,
    val price: Float,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

package com.example.crudprestomart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val code: String,
    val categoryId: Long,
    val categoryName: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val minStock: Int,
    val unit: String,
    val supplier: String,
    val expirationDate: String,
    val imageUrl: String? = null,
    val registrationDate: Long = System.currentTimeMillis()
)
    
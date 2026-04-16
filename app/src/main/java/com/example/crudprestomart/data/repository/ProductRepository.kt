package com.example.crudprestomart.data.repository

import com.example.crudprestomart.data.local.ProductDao
import com.example.crudprestomart.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val productCount: Flow<Int> = productDao.getProductCount()
    val lowStockCount: Flow<Int> = productDao.getLowStockCount()
    val categoryCount: Flow<Int> = productDao.getCategoryCount()

    suspend fun getProductById(id: Long): Product? = productDao.getProductById(id)
    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)
    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}

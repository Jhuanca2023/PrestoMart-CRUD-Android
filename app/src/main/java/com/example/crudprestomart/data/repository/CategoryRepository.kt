package com.example.crudprestomart.data.repository

import com.example.crudprestomart.data.local.CategoryDao
import com.example.crudprestomart.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
    val categoryCount: Flow<Int> = categoryDao.getCategoryCount()

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
}

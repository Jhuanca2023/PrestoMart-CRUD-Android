package com.example.crudprestomart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.crudprestomart.data.model.Product
import com.example.crudprestomart.data.repository.CategoryRepository
import com.example.crudprestomart.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date

class ProductViewModel(
    private val repository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _lastUpdated = MutableStateFlow(Date())


    val lastUpdatedText: Flow<String> = _lastUpdated.flatMapLatest { lastUpdate ->
        flow {
            while (true) {
                emit(getTimeAgo(lastUpdate))
                delay(60000)
            }
        }
    }

    val filteredProducts: Flow<List<Product>> = _searchQuery
        .flatMapLatest { query ->
            repository.allProducts.map { products ->
                if (query.isEmpty()) {
                    products
                } else {
                    products.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.code.contains(query, ignoreCase = true) ||
                                it.categoryName.contains(query, ignoreCase = true)
                    }
                }
            }
        }

    val productCount: Flow<Int> = repository.productCount
    val lowStockCount: Flow<Int> = repository.lowStockCount
    val categoryCount: Flow<Int> = categoryRepository.categoryCount

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun getProductById(id: Long, onResult: (Product?) -> Unit) {
        viewModelScope.launch {
            val product = repository.getProductById(id)
            onResult(product)
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
            _lastUpdated.value = Date()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
            _lastUpdated.value = Date()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            _lastUpdated.value = Date()
        }
    }

    fun refreshLastUpdated() {
        _lastUpdated.value = Date()
    }

    private fun getTimeAgo(date: Date): String {
        val diff = Date().time - date.time
        val minutes = (diff / (1000 * 60)).toInt()
        return when {
            minutes < 1 -> "Actualizado ahora"
            minutes < 60 -> "Actualizado hace ${minutes}m"
            minutes < 1440 -> "Actualizado hace ${minutes / 60}h"
            else -> "Actualizado hace ${minutes / 1440}d"
        }
    }
}

class ProductViewModelFactory(
    private val repository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

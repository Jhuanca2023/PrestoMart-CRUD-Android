package com.example.crudprestomart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crudprestomart.data.local.AppDatabase
import com.example.crudprestomart.data.model.Category
import com.example.crudprestomart.data.model.Product
import com.example.crudprestomart.data.repository.CategoryRepository
import com.example.crudprestomart.data.repository.ProductRepository
import com.example.crudprestomart.ui.components.DeleteConfirmationDialog
import com.example.crudprestomart.ui.components.PrestoMartBottomNavigation
import com.example.crudprestomart.ui.screens.categories.CategoryFormScreen
import com.example.crudprestomart.ui.screens.categories.CategoryScreen
import com.example.crudprestomart.ui.screens.detail.ProductDetailScreen
import com.example.crudprestomart.ui.screens.form.ProductFormScreen
import com.example.crudprestomart.ui.screens.list.ProductListScreen
import com.example.crudprestomart.ui.theme.CrudPrestomartTheme
import com.example.crudprestomart.ui.viewmodel.CategoryViewModel
import com.example.crudprestomart.ui.viewmodel.CategoryViewModelFactory
import com.example.crudprestomart.ui.viewmodel.ProductViewModel
import com.example.crudprestomart.ui.viewmodel.ProductViewModelFactory
import androidx.compose.material3.ExperimentalMaterial3Api

class MainActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val productRepository by lazy { ProductRepository(database.productDao()) }
    private val categoryRepository by lazy { CategoryRepository(database.categoryDao()) }
    
    private val productViewModel: ProductViewModel by viewModels { ProductViewModelFactory(productRepository, categoryRepository) }
    private val categoryViewModel: CategoryViewModel by viewModels { CategoryViewModelFactory(categoryRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrudPrestomartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrestoMartApp(productViewModel, categoryViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestoMartApp(
    productViewModel: ProductViewModel,
    categoryViewModel: CategoryViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showProductFormSheet by remember { mutableStateOf(false) }
    var showCategoryFormSheet by remember { mutableStateOf(false) }
    var selectedProductForEdit by remember { mutableStateOf<Product?>(null) }
    var selectedCategoryForEdit by remember { mutableStateOf<Category?>(null) }
    var showDeleteDialog by remember { mutableStateOf<Product?>(null) }
    
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        bottomBar = {
            if (currentRoute != null && (currentRoute == "list" || currentRoute == "categories" || currentRoute == "config")) {
                PrestoMartBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController, 
            startDestination = "list",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("list") {
                ProductListScreen(
                    viewModel = productViewModel,
                    onProductClick = { productId ->
                        navController.navigate("detail/$productId")
                    },
                    onAddProductClick = {
                        selectedProductForEdit = null
                        showProductFormSheet = true
                    }
                )
            }
            composable("categories") {
                CategoryScreen(
                    viewModel = categoryViewModel,
                    onAddCategoryClick = {
                        selectedCategoryForEdit = null
                        showCategoryFormSheet = true
                    },
                    onEditCategoryClick = { category ->
                        selectedCategoryForEdit = category
                        showCategoryFormSheet = true
                    }
                )
            }
            composable("config") {
                // Config screen placeholder
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Config design could go here
                }
            }
            composable(
                route = "detail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.LongType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getLong("productId") ?: return@composable
                var product by remember { mutableStateOf<Product?>(null) }
                
                LaunchedEffect(productId) {
                    productViewModel.getProductById(productId) {
                        product = it
                    }
                }

                product?.let { currentProduct ->
                    ProductDetailScreen(
                        product = currentProduct,
                        onBack = { navController.popBackStack() },
                        onEdit = {
                            selectedProductForEdit = currentProduct
                            showProductFormSheet = true
                        },
                        onDelete = {
                            showDeleteDialog = currentProduct
                        }
                    )
                }
            }
        }
    }

    // Add/Edit Product Bottom Sheet
    if (showProductFormSheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                showProductFormSheet = false
                selectedProductForEdit = null
            },
            sheetState = sheetState
        ) {
            ProductFormScreen(
                product = selectedProductForEdit,
                categoryViewModel = categoryViewModel,
                onDismiss = { 
                    showProductFormSheet = false
                    selectedProductForEdit = null
                },
                onSave = { product ->
                    if (selectedProductForEdit == null) {
                        productViewModel.insertProduct(product)
                    } else {
                        productViewModel.updateProduct(product)
                    }
                    showProductFormSheet = false
                    selectedProductForEdit = null
                }
            )
        }
    }

    // Add/Edit Category Bottom Sheet
    if (showCategoryFormSheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                showCategoryFormSheet = false
                selectedCategoryForEdit = null
            },
            sheetState = sheetState
        ) {
            CategoryFormScreen(
                category = selectedCategoryForEdit,
                onDismiss = { 
                    showCategoryFormSheet = false
                    selectedCategoryForEdit = null
                },
                onSave = { category ->
                    if (selectedCategoryForEdit == null) {
                        categoryViewModel.insertCategory(category)
                    } else {
                        categoryViewModel.updateCategory(category)
                    }
                    productViewModel.refreshLastUpdated()
                    showCategoryFormSheet = false
                    selectedCategoryForEdit = null
                }
            )
        }
    }

    // Confirmacion de Eliminacion
    showDeleteDialog?.let { product ->
        DeleteConfirmationDialog(
            productName = product.name,
            onConfirm = {
                productViewModel.deleteProduct(product)
                showDeleteDialog = null
                if (currentRoute?.startsWith("detail") == true) {
                    navController.popBackStack()
                }
            },
            onDismiss = { showDeleteDialog = null }
        )
    }
}

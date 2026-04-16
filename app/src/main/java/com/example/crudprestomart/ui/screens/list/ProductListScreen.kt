package com.example.crudprestomart.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudprestomart.data.model.Product
import com.example.crudprestomart.ui.components.CustomSearchBar
import com.example.crudprestomart.ui.components.DeleteConfirmationDialog
import com.example.crudprestomart.ui.components.ProductCard
import com.example.crudprestomart.ui.components.StatCard
import com.example.crudprestomart.ui.theme.PrestoLightGray
import com.example.crudprestomart.ui.theme.PrestoRed
import com.example.crudprestomart.ui.theme.PrestoYellow
import com.example.crudprestomart.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onProductClick: (Long) -> Unit,
    onAddProductClick: () -> Unit
) {
    val products by viewModel.filteredProducts.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val productCount by viewModel.productCount.collectAsState(initial = 0)
    val lowStockCount by viewModel.lowStockCount.collectAsState(initial = 0)
    val categoryCount by viewModel.categoryCount.collectAsState(initial = 0)
    val lastUpdatedText by viewModel.lastUpdatedText.collectAsState(initial = "Actualizado ahora")

    var showDeleteDialog by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = {
            Surface(
                color = PrestoRed,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Presto",
                            color = PrestoYellow,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "mart",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                    }
                    
                    OutlinedButton(
                        onClick = { /* Filter */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        border = null,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(
                            Icons.Default.FilterList, 
                            contentDescription = "Filtros", 
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Filtros", 
                            color = Color.White, 
                            fontSize = 12.sp, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProductClick,
                containerColor = PrestoRed,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PrestoLightGray)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrestoLightGray)
                    .padding(16.dp)
            ) {
                CustomSearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) }
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "PRODUCTOS",
                    value = productCount.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "STOCK BAJO",
                    value = lowStockCount.toString(),
                    isAlert = true,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "CATEGORÍAS",
                    value = categoryCount.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            // List Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LISTADO RECIENTE",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBDBDBD),
                    fontSize = 12.sp
                )
                Text(
                    text = lastUpdatedText,
                    color = Color(0xFFBDBDBD),
                    fontSize = 12.sp
                )
            }

            // Product List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                showDeleteDialog = product
                                false
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            val color = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> PrestoRed
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.White
                                )
                            }
                        },
                        enableDismissFromStartToEnd = false
                    ) {
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    showDeleteDialog?.let { product ->
        DeleteConfirmationDialog(
            productName = product.name,
            onConfirm = {
                viewModel.deleteProduct(product)
                showDeleteDialog = null
            },
            onDismiss = { showDeleteDialog = null }
        )
    }
}

package com.example.crudprestomart.ui.screens.form

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.crudprestomart.data.model.Category
import com.example.crudprestomart.data.model.Product
import com.example.crudprestomart.ui.theme.PrestoRed
import com.example.crudprestomart.ui.theme.PrestoTextGray
import com.example.crudprestomart.ui.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    product: Product? = null,
    categoryViewModel: CategoryViewModel,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val dbCategories by categoryViewModel.allCategories.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf(product?.name ?: "") }
    var code by remember { mutableStateOf(product?.code ?: "") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }
    var minStock by remember { mutableStateOf(product?.minStock?.toString() ?: "") }
    var unit by remember { mutableStateOf(product?.unit ?: "Unidad") }
    var supplier by remember { mutableStateOf(product?.supplier ?: "") }
    var expirationDate by remember { mutableStateOf(product?.expirationDate ?: "") }
    var imageUrl by remember { mutableStateOf(product?.imageUrl ?: "") }

    val units = listOf("Unidad", "Caja", "Kg", "Litro")


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { imageUrl = it.toString() }
    }


    LaunchedEffect(dbCategories) {
        if (product != null && selectedCategory == null) {
            selectedCategory = dbCategories.find { it.id == product.categoryId }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(PrestoRed, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AddBusiness, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (product == null) "Agregar Producto" else "Editar Producto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Ingresa los detalles del artículo",
                        fontSize = 12.sp,
                        color = PrestoTextGray
                    )
                }
            }
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        SectionHeader(title = "INFORMACIÓN GENERAL")
        
        CustomTextField(label = "Nombre del Producto", value = name, onValueChange = { name = it }, placeholder = "Ej: Aceite Primor 1L")
        CustomTextField(
            label = "Código / SKU", 
            value = code, 
            onValueChange = { code = it }, 
            placeholder = "775012345678",
            trailingIcon = { Icon(Icons.Default.QrCodeScanner, contentDescription = null) }
        )
        
        CategoryDropdown(
            label = "Categoría",
            selectedCategory = selectedCategory,
            categories = dbCategories,
            onCategorySelected = { selectedCategory = it }
        )
        
        CustomTextField(label = "Proveedor", value = supplier, onValueChange = { supplier = it }, placeholder = "Nombre del distribuidor")
        CustomTextField(label = "Descripción (Opcional)", value = description, onValueChange = { description = it }, placeholder = "Detalles adicionales", singleLine = false)

        Spacer(modifier = Modifier.height(16.dp))


        SectionHeader(title = "CONTROL DE EXISTENCIAS")
        
        CustomTextField(
            label = "Precio de Venta (S/)", 
            value = price, 
            onValueChange = { price = it }, 
            placeholder = "S/ 0.00",
            keyboardType = KeyboardType.Decimal
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                DropdownField(label = "Unidad", selectedOption = unit, options = units) { unit = it }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(label = "Vencimiento", value = expirationDate, onValueChange = { expirationDate = it }, placeholder = "mm/dd/yyyy")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(label = "Stock Actual", value = stock, onValueChange = { stock = it }, placeholder = "0", keyboardType = KeyboardType.Number)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(label = "Mínimo Crítico", value = minStock, onValueChange = { minStock = it }, placeholder = "5", keyboardType = KeyboardType.Number)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        SectionHeader(title = "VISUAL DEL PRODUCTO")
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
                .clickable { launcher.launch("image/*") }
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Vista previa",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = PrestoTextGray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Subir Foto", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Añadir una imagen ayuda a los operarios...", fontSize = 12.sp, color = PrestoTextGray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            ) {
                Text("Descartar", color = PrestoTextGray)
            }
            Button(
                onClick = {
                    if (selectedCategory != null && name.isNotEmpty()) {
                        val newProduct = Product(
                            id = product?.id ?: 0,
                            name = name,
                            code = code,
                            categoryId = selectedCategory!!.id,
                            categoryName = selectedCategory!!.name,
                            description = description,
                            price = price.toDoubleOrNull() ?: 0.0,
                            stock = stock.toIntOrNull() ?: 0,
                            minStock = minStock.toIntOrNull() ?: 0,
                            unit = unit,
                            supplier = supplier,
                            expirationDate = expirationDate,
                            imageUrl = imageUrl
                        )
                        onSave(newProduct)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrestoRed,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = selectedCategory != null && name.isNotEmpty()
            ) {
                Text("Guardar Producto", color = Color.White)
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color(0xFF8B6B23),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrestoTextGray)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    label: String,
    selectedCategory: Category?,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrestoTextGray)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCategory?.name ?: "Seleccionar categoría",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (categories.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No hay categorías. Crea una primero.") },
                        onClick = { expanded = false }
                    )
                }
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text("${category.icon} ${category.name}") },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrestoTextGray)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

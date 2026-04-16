package com.example.crudprestomart.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudprestomart.data.model.Category
import com.example.crudprestomart.ui.theme.PrestoRed
import com.example.crudprestomart.ui.theme.PrestoTextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFormScreen(
    category: Category? = null,
    onDismiss: () -> Unit,
    onSave: (Category) -> Unit
) {
    var name by remember { mutableStateOf(category?.name ?: "") }
    var icon by remember { mutableStateOf(category?.icon ?: "📦") }

    val icons = listOf(
        "📦", "🍎", "🥛", "🍞", "🥤", "🧼", "🍖", "🥦", "🍫", "🔋",
        "🥪", "🍣", "🍦", "🍷", "🧴", "🧻", "🧹", "🔦", "🍳", "🥣"
    )

    val iconScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
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
                    Icon(Icons.Default.Widgets, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (category == null) "Nueva Categoría" else "Editar Categoría",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Ingresa el nombre y un icono",
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

        // Name
        Text(text = "Nombre de la Categoría", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrestoTextGray)
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ej: Lácteos") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Icon Selector
        Text(text = "Seleccionar Icono", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrestoTextGray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(iconScrollState),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            icons.forEach { emoji ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (icon == emoji) PrestoRed.copy(alpha = 0.1f) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { icon = emoji },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 28.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Buttons
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
                    if (name.isNotEmpty()) {
                        onSave(Category(id = category?.id ?: 0, name = name, icon = icon))
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = PrestoRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Guardar Categoría", color = Color.White)
            }
        }
    }
}

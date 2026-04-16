package com.example.crudprestomart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudprestomart.ui.theme.PrestoRed

@Composable
fun PrestoMartBottomNavigation(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(80.dp)
    ) {
        BottomNavItem(
            label = "PRODUCTOS",
            icon = Icons.Default.Inventory,
            isSelected = currentRoute == "list" || currentRoute?.startsWith("detail") == true,
            onClick = { onNavigate("list") }
        )
        BottomNavItem(
            label = "CATEGORÍAS",
            icon = Icons.Default.Widgets,
            isSelected = currentRoute == "categories",
            onClick = { onNavigate("categories") }
        )
        BottomNavItem(
            label = "CONFIG",
            icon = Icons.Default.Settings,
            isSelected = currentRoute == "config",
            onClick = { onNavigate("config") }
        )
    }
}

@Composable
fun RowScope.BottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) PrestoRed else Color.Gray
    val bgColor = if (isSelected) Color(0xFFFFEBEE) else Color.Transparent

    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Box(
                modifier = Modifier
                    .background(bgColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        label = {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}

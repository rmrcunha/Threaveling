package com.example.threaveling.models

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val title: String,
    val icon: ImageVector? = null,
    val onClick: () -> Unit
)
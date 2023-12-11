package com.bangkit.hijalearn.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.bangkit.hijalearn.navigation.Screen

data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)
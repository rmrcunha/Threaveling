package com.example.threaveling.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threaveling.ui.theme.BLACK
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE

@Composable
fun BottomBarApp(focusHome:Color = BLACK,
                 focusSearch:Color = BLACK,
                 focusFavorites:Color = BLACK,
                 onClickHome:()->Unit = {},
                 onClickSearch:()->Unit = {},
                 onClickFavorites:()->Unit ={}){
    BottomAppBar(
        modifier = Modifier.border(width = 1.dp, color = LIGHT_BLUE),
        tonalElevation = 120.dp,
        contentColor = BLACK,
        containerColor = WHITE
    ) {
        Row(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                IconButton(onClick = onClickHome,
                    modifier = Modifier.align(Alignment.TopStart),
                    colors = IconButtonColors(containerColor = WHITE, contentColor =  focusHome,
                        disabledContentColor = BLACK, disabledContainerColor = WHITE)
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "Go to home page")
                }
                IconButton(onClick = onClickSearch,
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = IconButtonColors(containerColor = WHITE, contentColor =  focusSearch,
                        disabledContentColor = BLACK, disabledContainerColor = WHITE)) {
                    Icon(Icons.Filled.Search, contentDescription = "Go to search page")
                }
                IconButton(onClick = onClickFavorites,
                    modifier = Modifier.align(Alignment.TopEnd),
                    colors = IconButtonColors(containerColor = WHITE, contentColor =  focusFavorites,
                        disabledContentColor = BLACK, disabledContainerColor = WHITE)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Go to favorites page")
                }
            }

        }
    }
}

@Composable
@Preview
private fun BottomBarAppPreview(){
    BottomBarApp()
}

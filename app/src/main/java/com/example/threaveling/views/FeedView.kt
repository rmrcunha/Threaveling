package com.example.threaveling.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.ui.theme.WHITE

@Composable
fun FeedView(navController:NavController){
    Scaffold(
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling", hasBack = false, hasOption = false)
        }
    ) { innerpadding ->
        Column { Modifier.padding(innerpadding) }
    }
}

@Composable
@Preview
private fun FeedViewPreview(){
    val navController = rememberNavController()
    FeedView(navController)
}
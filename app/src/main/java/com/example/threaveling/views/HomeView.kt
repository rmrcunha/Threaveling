package com.example.threaveling.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication
import com.example.threaveling.R
import com.example.threaveling.components.BottomBarApp
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.models.MenuItem
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE

@Composable
fun HomeView(navController:NavController){
    val state = remember { mutableStateOf(false)  }
    val items = listOf(
        MenuItem(title = "Logout",
            onClick = {
                FirebaseAuthentication.out()
                navController.navigate("Login")

            }))

    Scaffold(
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling",
                hasBack = false,
                hasOption = true,
                isExpanded = state,
                menuItems = items
                )},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp),
                onClick = { navController.navigate("SelectTravel") },
                containerColor = LIGHT_BLUE,
                contentColor = WHITE
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_create_24),
                    contentDescription = "Create new post"
                )
            }
        },
        bottomBar = {
        BottomBarApp(focusHome = LIGHT_BLUE,
            onClickHome = { navController.navigate("Home") })
        }
    ) { innerpadding ->
        Column(Modifier.padding(innerpadding).fillMaxSize()) {
        }

    }
}

@Composable
@Preview
private fun FeedViewPreview(){
    val navController = rememberNavController()
    HomeView(navController)
}
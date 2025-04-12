package com.example.threaveling.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.R
import com.example.threaveling.components.BottomBarApp
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE

@Composable
fun PostView(navController: NavController){
    Scaffold(
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling",
                hasBack = true,
                hasOption = false,
            )
        },
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
    ){
        innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)){

        }
    }

}

@Composable
@Preview
fun PostViewPreview(){
    val navController = rememberNavController()
    PostView(navController)
}

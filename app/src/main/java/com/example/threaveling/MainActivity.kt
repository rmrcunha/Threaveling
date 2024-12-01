package com.example.threaveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.ui.theme.ThreavelingTheme
import com.example.threaveling.views.LoginView
import com.example.threaveling.views.SignUpView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThreavelingTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Login"){
                    composable(
                        route = "Login"
                    ){
                        LoginView(navController)
                    }
                    composable(
                        route = "SignUp"
                    ){
                        SignUpView(navController)
                    }
                }
            }
        }
    }
}



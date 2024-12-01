package com.example.threaveling.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.R
import com.example.threaveling.components.AppButton
import com.example.threaveling.components.SocialLogButton
import com.example.threaveling.components.TextInput
import com.example.threaveling.ui.theme.WHITE

@Composable
fun LoginView(navController: NavController){
        Scaffold(containerColor = WHITE){
                innerPadding ->
                        var email by remember { mutableStateOf("")}
                        var password by remember { mutableStateOf("") }

                        Column (
                                modifier = Modifier.
                                        padding(innerPadding)
                                        .background(color = WHITE)
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                        ){
                                OutlinedCard(
                                        Modifier.background(color = Color.White)
                                ){
                                        Column(
                                                Modifier
                                                        .background(color = Color.White),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                        ){
                                                Text(
                                                        text = "Threaveling",
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 25.sp,
                                                        fontFamily = FontFamily.Serif,
                                                        modifier = Modifier.padding().padding(10.dp)

                                                )
                                                TextInput(value = email,
                                                        {email = it},
                                                        Modifier
                                                        .size(width = 320.dp, height = 85.dp)
                                                        .padding()
                                                        .padding(10.dp)
                                                        .fillMaxSize()
                                                        .background(color = WHITE) ,
                                                        label = "Email")

                                                TextInput(value = password,
                                                        {password = it},
                                                        Modifier
                                                                .size(width = 320.dp, height = 85.dp)
                                                                .padding()
                                                                .padding(10.dp)
                                                                .fillMaxSize()
                                                                .background(color = WHITE) ,
                                                        label = "Password",
                                                        visualTransformation = PasswordVisualTransformation())

                                                AppButton({ TODO("Autenticacao")},
                                                        text = "Login",
                                                        modifier = Modifier.size(width = 300.dp, height = 55.dp)
                                                        .padding()
                                                        .padding(10.dp)
                                                        .fillMaxSize()
                                                )
                                                AppButton({  navController.navigate("SignUp")},
                                                        text = "Sign up",
                                                        modifier = Modifier.size(width = 300.dp, height = 55.dp)
                                                                .padding()
                                                                .padding(10.dp)
                                                                .fillMaxSize()
                                                )

                                                Row {
                                                        SocialLogButton({},
                                                                ImageVector.vectorResource(R.drawable.ic_google_logo),
                                                                description = "Login usando conta google"
                                                        )
                                                        SocialLogButton({},
                                                                ImageVector.vectorResource(R.drawable.ic_facebook_logo),
                                                                description = "Login usando conta do facebook"
                                                        )
                                                        SocialLogButton({},
                                                                ImageVector.vectorResource(R.drawable.ic_x_logo),
                                                                description = "Login usando conta do X"
                                                        )
                                                }
                                        }
                                }
                        }
        }
}

@Preview
@Composable
private fun LoginViewPreview(){
        val navController = rememberNavController()
        LoginView(navController)
}
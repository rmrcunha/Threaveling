package com.example.threaveling.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.FirebaseAuthentication.AuthenticationCallback
import com.example.threaveling.FirebaseAuthentication.FirebaseAuthentication
import com.example.threaveling.R
import com.example.threaveling.components.AppButton
import com.example.threaveling.components.SocialLogButton
import com.example.threaveling.components.TextInput
import com.example.threaveling.models.UserModel
import com.example.threaveling.ui.theme.WHITE
import kotlinx.coroutines.launch

@Composable
fun SignUpView(navController: NavController) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(  snackbarHost = {
        SnackbarHost(hostState = snackbarHostState,
            )
        },
        containerColor = WHITE){
            innerPadding ->
        var username by remember {mutableStateOf("")}
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordConfirmation by remember { mutableStateOf("") }

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
                Modifier.background(color = Color.White).fillMaxWidth()
            ){
                Column(
                    Modifier
                        .background(color = Color.White)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Threaveling",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding().padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)

                    )
                    TextInput(value = username,
                        {username = it},
                        Modifier
                            .size(width = 390.dp, height = 85.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                            .fillMaxSize()
                            .background(color = WHITE) ,
                        label = "Username")

                    TextInput(value = email,
                        {email = it},
                        Modifier
                            .size(width = 390.dp, height = 85.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                            .fillMaxSize()
                            .background(color = WHITE) ,
                        label = "Email",
                        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email)
                    )

                    TextInput(value = password,
                        {password = it},
                        Modifier
                            .size(width = 390.dp, height = 85.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                            .fillMaxSize()
                            .background(color = WHITE) ,
                        label = "Password",
                        visualTransformation = PasswordVisualTransformation()
                    )
                    TextInput(value = passwordConfirmation,
                        {passwordConfirmation = it},
                        Modifier
                            .size(width = 390.dp, height = 85.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                            .fillMaxSize()
                            .background(color = WHITE) ,
                        label = "Repeat the password",
                        visualTransformation = PasswordVisualTransformation()
                    )

                    AppButton({
                        if(email.isEmpty() || username.isEmpty() || password.isEmpty()){
                            scope.launch {
                                snackbarHostState.showSnackbar("Preencha todos os campos corretamente")
                            }
                        } else if(password != passwordConfirmation){
                            scope.launch {
                                snackbarHostState.showSnackbar("As senhas não coincidem")
                            }
                        }
                        else{
                            FirebaseAuthentication.createUserWithEmailAndPassword(username = username,
                                email = email,
                                password = password,
                                navController = {navController.navigate("Home")},
                                object : AuthenticationCallback {
                                    override fun onSuccess(user: UserModel) {
                                        Log.d("AuthCallback", "Usuário criado com sucesso: ${user.getUsername()}")
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Usuário criado com sucesso")
                                        }
                                    }

                                    override fun onFailure(errorMessage: String) {
                                        Log.e("AuthCallback", "Erro ao criar usuário: $errorMessage")
                                        scope.launch {
                                            snackbarHostState.showSnackbar(errorMessage)
                                        }
                                    }
                                }
                            )
                        }
                    },
                        text = "Sign up",
                        modifier = Modifier.size(width = 390.dp, height = 55.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                            .fillMaxSize()
                    )
                    AppButton({  navController.navigate("Login")},
                        text = "Login",
                        modifier = Modifier.size(width = 300.dp, height = 55.dp)
                            .padding()
                            .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
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

@Composable
@Preview
private fun SignUpViewPreview(){
    val navController = rememberNavController()
    SignUpView(navController)
}
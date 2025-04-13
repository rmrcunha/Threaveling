package com.example.threaveling.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threaveling.R
import com.example.threaveling.components.AppButton
import com.example.threaveling.components.TextInput
import com.example.threaveling.ui.theme.WHITE
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.cloudinary.transformation.RoundCorners
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.Resize
import com.example.threaveling.cloudinary.CloudinaryConfig.cloudinary
import com.example.threaveling.cloudinary.CloudinaryUpload
import com.example.threaveling.datasource.UserDataSource
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId
import com.example.threaveling.getBitmapFromImage
import com.example.threaveling.models.UserModel
import kotlinx.coroutines.launch

@Composable
fun UserCreateView(onActionRequest: (String) -> Unit = {}, navController: NavController){
    val scope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val defaultImage = getBitmapFromImage(context, R.drawable.baseline_person_24).asImageBitmap()
    val dataSource = UserDataSource()

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        onActionRequest(uri.toString())
        imageUri = uri
    }

    Scaffold(
        containerColor = WHITE
    ){
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.White)
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            OutlinedButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .size(350.dp, 350.dp)
                    .padding(bottom = 10.dp)
                    .align(Alignment.CenterHorizontally),
                shape = ButtonDefaults.outlinedShape,
            ) {
                if (imageUri != null) {
                    var userImage by remember{ mutableStateOf(null as String?)}
                    CloudinaryUpload.uploadImage(context, imageUri!!,getCurrentUserId(), { imageUrl->
                        Log.d("Upload", "Upload Success $imageUrl")
                        userImage = cloudinary.image{
                            publicId("${getCurrentUserId()}/${getCurrentUserId()}")
                            resize(Resize.thumbnail(){
                                width(1.0F)
                                aspectRatio(1.0F)
                                gravity(
                                    Gravity.focusOn(
                                        FocusOn.face()
                                    )
                                )
                            })
                            roundCorners(RoundCorners.max())
                        }.generate()
                    }, {error ->
                        Log.e("Upload", "Upload Error $error")
                    } )
                    AsyncImage(
                        model = userImage,
                        contentDescription = "Imagem de perfil",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } else{
                    Image(
                        bitmap = defaultImage,
                        contentDescription = "Imagem de perfil",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }


                TextInput(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.size(width = 320.dp, height = 65.dp).padding(bottom = 10.dp),
                    label = "Escolha um nome de usuário",
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    lines = 1,
                )


                AppButton(
                    onClick = { scope.launch{
                        dataSource.userDetails(UserModel(username = username, email = FirebaseAuthentication.userEmail()),{
                            Log.d("DataSource", "DataSource Success userDetails")
                            navController.navigate("Home")
                        }, {
                            Log.e("DataSource", "DataSource Error userDetails")
                        })
                    } },
                    text = "Go Threavel",
                    modifier = Modifier.size(width = 320.dp, height = 65.dp).padding(top = 25.dp),
                )

        }
    }
}

@Composable
@Preview
private fun UserCreateViewPreview(){
    //UserCreateView()

}
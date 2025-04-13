package com.example.threaveling.components

import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.cloudinary.CloudinaryUpload
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId
import com.example.threaveling.models.Threavel
import com.example.threaveling.repositories.ThreavelRepository
import com.example.threaveling.ui.theme.LIGHT_BLUE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale
import java.util.UUID

const val TAG = "ThreavelPostBottomSheet"

@Composable
fun PartialBottomSheet(navController: NavController, place:String, introduction:String, description:String, stay:String){
    var stayDescription by remember { mutableStateOf(stay) }
    var introductionText by remember { mutableStateOf(introduction) }
    var descriptionText by remember { mutableStateOf(description) }
    var uriList:List<Uri>

    var showRangeModal by remember { mutableStateOf(false) }
    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }
    var formattedStartDate:String? = null
    var formattedEndDate:String? = null

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var newThreavel: Threavel
    val threavelRepository = remember { ThreavelRepository() }




    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Box{
            TopBarApp(
                title = "Minha viagem para o $place",
            )
        }
        TextInput(
            value = introductionText,
            onValueChange = { introductionText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            label = "Faça uma breve introdução sobre sua viagem",
            lines = 10
        )

        AppButton(
            onClick = {
                showRangeModal = true
            },
            text = "Selecione o periodo da sua viagem",
            modifier = Modifier
                .size(width = 390.dp, height = 55.dp)
                .padding(top = 10.dp, bottom = 10.dp)

        )

        if (selectedDateRange.first != null && selectedDateRange.second != null) {
            val startDate = Date(selectedDateRange.first!!)
            val endDate = Date(selectedDateRange.second!!)
            formattedStartDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(startDate)
            formattedEndDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(endDate)
            Text("$formattedStartDate - $formattedEndDate")
        }

        if (showRangeModal) {
            DateRangePickerModal(
                onDateRangeSelected = {
                    selectedDateRange = it
                    showRangeModal = false
                },
                onDismiss = { showRangeModal = false }
            )
        }

        uriList = uploadRow()

        TextInput(
            value = descriptionText,
            onValueChange = { descriptionText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            label = "Descreva melhor a sua viagem!",
            lines = 10
        )
        TextInput(
            value = stayDescription,
            onValueChange = { stayDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            label = "Descreva sua estadia!",
            lines = 10
        )

        AppButton(
            onClick = {
                val postId = UUID.randomUUID().toString()
                newThreavel = Threavel(
                    id = postId,
                    userId = getCurrentUserId(),
                    destiny = place,
                    introduction = introductionText,
                    date = mapOf("start" to formattedStartDate, "end" to formattedEndDate),
                    detailedDescription = descriptionText,
                    stayDescription = stayDescription,
                    picsNumber = uriList.size,
                )

                if(newThreavel.introduction.isEmpty()){
                    Toast.makeText(
                        context,
                        "Coloque uma introdução para sua viagem",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if(newThreavel.detailedDescription.isEmpty()){
                    Toast.makeText(
                        context,
                        "Coloque uma descrição para sua viagem",
                        Toast.LENGTH_SHORT
                    ).show()
                }  else{
                    scope.launch(Dispatchers.IO) {
                        Log.d(TAG,"Tentativa envio mensagem Step 1 $newThreavel")
                        threavelRepository.savePost(newThreavel){
                            uriList.forEachIndexed { index, uri ->
                                CloudinaryUpload.uploadImage(context, uri,postId + "_${index}" , { imageUrl->
                                    Log.d("Upload", "Upload Success $imageUrl")
                                }, { error ->
                                    Log.e("Upload", "Upload Error $error")
                                })
                            }
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Postagem realizada com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("Home")
                        }

                    }
                }

            },
            text = "Threavel",
            modifier = Modifier
                .size(width = 390.dp, height = 55.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            color = LIGHT_BLUE
        )
    }

}

@Preview
@Composable
fun PartialBottomSheetPreview(){
    val navController = rememberNavController()
    PartialBottomSheet(navController,"Camboja", "", "", "")
}
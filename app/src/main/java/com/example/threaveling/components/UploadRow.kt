package com.example.threaveling.components

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threaveling.R
import com.example.threaveling.ui.theme.BLACK


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun uploadRow(onActionRequest: (String) -> Unit = {}):List<Uri>{

    var imagesUri by remember { mutableStateOf(emptyList<Uri>()) }
    val context = LocalContext.current
    var thumbnail by remember { mutableStateOf(null  as Bitmap?)}

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()){ uri ->
        onActionRequest(uri.toString())
        Log.d("UploadRow", "URI: $uri")
        imagesUri = uri
    }



    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)){
        Column(Modifier.align(Alignment.Center)){
            Text("Mostre suas fotos", modifier = Modifier.align(Alignment.CenterHorizontally))
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .align(Alignment.CenterHorizontally)) {
                OutlinedButton(
                    onClick = {
                        galleryLauncher.launch("image/*")

                    },
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp)
                        .padding(10.dp)
                        .padding(),
                    shape = ButtonDefaults.outlinedShape
                ){
                    Image(
                        ImageVector.vectorResource(R.drawable.baseline_add_24),
                        contentDescription = "Upload",
                        colorFilter = ColorFilter.tint(BLACK),
                    )
                }

                imagesUri.forEach { uri ->
                    thumbnail = context.contentResolver.loadThumbnail(uri, Size(100, 100), null)
                    Image(thumbnail!!.asImageBitmap(), contentDescription = "Contet Uploaded", modifier = Modifier.padding(10.dp).size(100.dp))
                }
            }
        }
    }
    return imagesUri
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun UploadRowPreview(){
    uploadRow()
}
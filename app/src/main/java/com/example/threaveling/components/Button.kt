package com.example.threaveling.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.threaveling.ui.theme.BLACK
import com.example.threaveling.ui.theme.LIGHT_BLUE

@Composable
fun AppButton(onClick:() -> Unit, text:String, modifier: Modifier, color:Color = LIGHT_BLUE) {
    ElevatedButton(
        onClick,

        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
    ) {
        Text(text)
    }
}

@Composable
fun AppButton(onClick:() -> Unit, text:String, modifier: Modifier, color:Color, borderWidth:Dp  = 2.dp, borderColor: Color){
    OutlinedButton(
        onClick,
        border = BorderStroke(borderWidth, borderColor),
        colors = ButtonDefaults.buttonColors(containerColor = color) ,
        modifier = modifier
    ) {
        Text(text)
    }
}

@Preview
@Composable
private fun ButtonPreview(){
    /*AppButton({},
        "Login",
        Modifier.size(width = 300.dp, height = 55.dp)
        .padding()
        .padding(10.dp)
        .fillMaxSize(),
        LIGHT_BLUE
    )*/

    AppButton(
        {},
        "Login",
        Modifier.size(width = 300.dp, height = 55.dp)
            .padding()
            .padding(10.dp)
            .fillMaxSize(),
        LIGHT_BLUE,
        borderColor = BLACK

    )
}
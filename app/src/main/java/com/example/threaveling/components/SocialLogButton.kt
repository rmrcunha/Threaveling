package com.example.threaveling.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.threaveling.R
import com.example.threaveling.ui.theme.WHITE

@Composable
fun SocialLogButton(onClick:() -> Unit, imageVector: ImageVector, description:String){
    Button(onClick,
            colors = ButtonColors(WHITE, WHITE, WHITE, WHITE) )
    {
        Image(
            imageVector = imageVector,
            contentDescription = description)
    }
}

@Composable
@Preview
private fun SocialLogButtonPreview(){
    SocialLogButton({}, ImageVector.vectorResource(R.drawable.ic_x_logo), "Facebook Logo")
}
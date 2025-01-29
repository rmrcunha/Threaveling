package com.example.threaveling.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threaveling.ui.theme.BLACK
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE

@Composable
fun TextInput(
    value:String,
    onValueChange:(String) -> Unit,
    modifier: Modifier,
    label:String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    lines:Int = 1
){
    OutlinedTextField(
        value = value,
        onValueChange,
        modifier,
        label = {Text(label)},
        maxLines = lines,
        colors = OutlinedTextFieldDefaults
            .colors(focusedTextColor = BLACK,
                focusedLabelColor = LIGHT_BLUE,
                focusedBorderColor = LIGHT_BLUE,
                unfocusedTextColor = BLACK,),
        shape = RoundedCornerShape(48.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
private fun TextInputPreview(){
    var text = ""
    TextInput(text,{text = it}, Modifier
        .size(width = 320.dp, height = 85.dp)
        .padding()
        .padding(10.dp)
        .fillMaxSize()
        .background(color = WHITE),"Email")
}
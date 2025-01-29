package com.example.threaveling.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.threaveling.R
import com.example.threaveling.models.MenuItem
import com.example.threaveling.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(title:String ,
              hasBack:Boolean = false,
              onClickBack:()->Unit = {} ,
              hasOption:Boolean = false,
              isExpanded:MutableState<Boolean> = mutableStateOf(false),
              menuItems:List<MenuItem> = listOf()){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LIGHT_BLUE,
            titleContentColor = WHITE,
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) },
        navigationIcon = {
            if (hasBack){
                IconButton(onClick = { onClickBack()}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            if(hasOption){
                IconButton(onClick = {
                    isExpanded.value = !isExpanded.value
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_more_vert_24),
                        contentDescription = "Localized description"
                    )
                }

                DropDown(isExpanded,menuItems, WHITE, BLACK)
            }
        }
    )

}

@Composable
@Preview
private fun TopBarAppPreview(){
    TopBarApp(title = "LearningCompose", hasBack = true, hasOption = true)
}
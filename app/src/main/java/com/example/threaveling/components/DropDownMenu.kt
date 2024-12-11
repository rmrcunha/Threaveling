package com.example.threaveling.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.threaveling.models.MenuItem

@Composable
fun DropDown(state:MutableState<Boolean>,items:List<MenuItem>, backgroundColor:Color, itemColor:Color){
    val itemPosition = remember { mutableStateOf(0) }

    DropdownMenu(
        expanded = state.value,
        onDismissRequest = {
            state.value = false
        },
        modifier = Modifier.background(backgroundColor, shape = RoundedCornerShape(5.dp))
    ) {
        items.forEachIndexed { index, menuItem ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        menuItem.icon?.let { icon ->
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Text(text = menuItem.title)
                    }
                },
                onClick = {
                    state.value = false
                    itemPosition.value = index
                    menuItem.onClick()
                },
                colors = MenuDefaults.itemColors(textColor = itemColor)
            )
        }
    }
}

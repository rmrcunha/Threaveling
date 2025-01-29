package com.example.threaveling.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.SECOND_LIGTH_BLUE
import com.example.threaveling.ui.theme.WHITE
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK", color = LIGHT_BLUE)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = LIGHT_BLUE)
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.DarkGray,
        )
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
                .background(color = Color.LightGray),
            colors = DatePickerDefaults.colors(
                containerColor = Color.DarkGray,
                titleContentColor = Color.LightGray,
                todayDateBorderColor = LIGHT_BLUE,
                todayContentColor = LIGHT_BLUE,
                selectedDayContainerColor = LIGHT_BLUE,
                dayInSelectionRangeContainerColor = SECOND_LIGTH_BLUE,
                navigationContentColor = LIGHT_BLUE,
                headlineContentColor = WHITE,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = LIGHT_BLUE,
                    focusedPlaceholderColor = LIGHT_BLUE
                )
            )
        )
    }
}

@Preview
@Composable

fun DateRangePickerModalPreview(){
    var showRangeModal by remember { mutableStateOf(false) }
    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Date range pickers:")

        Button(onClick = { showRangeModal = true }) {
            Text("Show Modal Date Range Picker")
        }

        if (selectedDateRange.first != null && selectedDateRange.second != null) {
            val startDate = Date(selectedDateRange.first!!)
            val endDate = Date(selectedDateRange.second!!)
            val formattedStartDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(startDate)
            val formattedEndDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(endDate)
            Text("Selected date range: $formattedStartDate - $formattedEndDate")
        } else {
            Text("No date range selected")
        }
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
}
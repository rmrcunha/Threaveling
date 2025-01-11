package com.example.threaveling.views


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.components.TextInput
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.mapbox.MapboxSearchBar
import com.example.threaveling.ui.theme.WHITE
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SelectTravelView(navController: NavController){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val placeAutoComplete = PlaceAutocomplete.create()
    var placeSugestion:List<PlaceAutocompleteSuggestion> = emptyList()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState,
        )
    },
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling",
                hasBack = true,
                onClickBack = {navController.navigate("Home")},
                hasOption = false,
            )
        }
    ) {
        innerPadding->

        var query by remember { mutableStateOf("") }

        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize())
        {
            Text(text = "Diga para onde foi sua viagem:",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 36.dp, bottom = 15.dp),
                fontSize = 22.sp
            )

            TextInput( value = query,
                {
                    query = it
                    scope.launch {
                        placeSugestion =
                            MapboxSearchBar(query, placeAutoComplete)
                        delay(1000)
                    }
                },
                Modifier
                    .size(width = 390.dp, height = 85.dp)
                    .padding()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                    .fillMaxSize()
                    .background(color = WHITE) ,
                label = "Onde foi sua viagem?"
            )
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ){
                items(placeSugestion){ suggestion ->
                    SuggestionItem(suggestion.name) {

                    }
                }
            }



        }
    }
}

@Composable
fun SuggestionItem(suggestion: String, onSelect: () -> Unit) {
    HorizontalDivider()
    Text(
        text = suggestion,
        modifier = Modifier
            .clickable { onSelect() }
            .padding(8.dp),
    )
    HorizontalDivider()
}


@Composable
@Preview
private fun SelectTravelViewPreview(){
    val navController = rememberNavController()
    SelectTravelView(navController)
}
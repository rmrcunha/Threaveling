package com.example.threaveling.views


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.R
import com.example.threaveling.components.PartialBottomSheet
import com.example.threaveling.components.TextInput
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.mapbox.mapboxSearchBar
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTravelView(navController: NavController){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val placeAutoComplete = PlaceAutocomplete.create()
    var placeSugestion:List<PlaceAutocompleteSuggestion> = emptyList()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling",
                hasBack = true,
                onClickBack = {navController.navigate("Home")},
                hasOption = false,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp),
                onClick = { showBottomSheet = true},
                containerColor = LIGHT_BLUE,
                contentColor = WHITE
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = "Create new post"
                )
            }
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
                            mapboxSearchBar(query, placeAutoComplete)
                    }
                },
                Modifier
                    .size(width = 390.dp, height = 85.dp)
                    .padding()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 0.dp)
                    .fillMaxSize()
                    .background(color = WHITE),
                label = "Onde foi sua viagem?"
            )
            LazyColumn (
                modifier = Modifier
                    .width(390.dp)
                    .padding(top = 1.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 1.dp, color = LIGHT_BLUE, shape = RoundedCornerShape(20.dp))
            ){
                items(placeSugestion){ suggestion ->
                    SuggestionItem(suggestion.name) {
                        query = suggestion.name
                    }
                    }
            }
            val introduction by remember { mutableStateOf("") }
            val description by remember { mutableStateOf("") }
            val stay by remember { mutableStateOf("") }

            if(showBottomSheet){
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = WHITE,
                    contentColor = LIGHT_BLUE,

                ){
                    PartialBottomSheet(navController, query, introduction, description, stay)
                }
            }


        }
    }
}

@Composable
fun SuggestionItem(suggestion: String, onSelect: () -> Unit) {
    Text(
        text = suggestion,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(8.dp),
        textAlign = TextAlign.Center
    )
}


@Composable
@Preview
private fun SelectTravelViewPreview(){
    val navController = rememberNavController()
    SelectTravelView(navController)
}
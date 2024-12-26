package com.example.threaveling.views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.threaveling.GeneralPorpose
import com.example.threaveling.components.TextInput
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.ui.theme.WHITE
import com.tomtom.sdk.common.ifSuccess
import com.tomtom.sdk.search.SearchCallback
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchFailure
import com.tomtom.sdk.search.model.SearchResultType
import com.tomtom.sdk.search.model.result.SearchResult
import com.tomtom.sdk.search.online.OnlineSearch
import kotlinx.coroutines.launch


@Composable
fun SelectTravelView(navController: NavController){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var searchText by remember { mutableStateOf("") }
    val context: Context = LocalContext.current

    val searchApi = OnlineSearch.create(context, GeneralPorpose.getApiKey())

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
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()){
            Text(text = "Diga para onde foi sua viagem:",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 36.dp, bottom = 15.dp),
                fontSize = 22.sp
            )

            TextInput(value = searchText,
                {searchText = it},
                modifier = Modifier
                    .size(width = 390.dp, height = 85.dp)
                    .padding()
                    .padding(start = 0.dp, end = 0.dp, top =  10.dp, bottom = 10.dp)
                    .fillMaxSize()
                    .background(color = WHITE) ,
                label = ""
            )

            val options = SearchOptions(query = searchText,
                resultTypes = setOf(SearchResultType.Area),
                limit = 5
            )

            val search = searchApi.search(searchOptions = options)
            if (search.isSuccess()) {
                search.ifSuccess{
                    searchResponse ->
                    Log.d("SearchCallback", "Busca realizada com sucesso: ${searchResponse.results.size}")
                }
            }
        }
    }
}

@Composable
@Preview
private fun SelectTravelViewPreview(){
    val navController = rememberNavController()
    SelectTravelView(navController)
}
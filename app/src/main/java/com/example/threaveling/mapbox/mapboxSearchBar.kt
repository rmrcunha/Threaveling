package com.example.threaveling.mapbox

import android.util.Log
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteOptions
import com.mapbox.search.autocomplete.PlaceAutocompleteType

suspend fun mapboxSearchBar(query: String, placeAutoComplete:PlaceAutocomplete): List<PlaceAutocompleteSuggestion> {

    val response = placeAutoComplete.suggestions(
        query = query,
        options = PlaceAutocompleteOptions(limit = 5, types = listOf(
            PlaceAutocompleteType.AdministrativeUnit.Country,
            PlaceAutocompleteType.AdministrativeUnit.Region,
            PlaceAutocompleteType.AdministrativeUnit.District,
            PlaceAutocompleteType.AdministrativeUnit.Place
        ))
    )


    if (response.isValue) {
        val suggestions = requireNotNull(response.value)

        Log.d("SearchApiExample", "Selected suggestion: \n$suggestions")

        return suggestions
    } else {
        Log.d("SearchApiExample", "Error getting suggestions: ${response.error}")
        return emptyList()
    }
}
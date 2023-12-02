package com.migvidal.viwiki2.ui.screens.search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.migvidal.viwiki2.data.network.search.Query
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SearchScreen(searchResults: List<Query.Search>?, onSearchClicked: (query: String) -> Unit) {

    Column {
        // search bar
        Button(onClick = { onSearchClicked.invoke("Banana") }) {
            Text(text = "Search for banana")
        }
        // recent searches
        if (searchResults.isNullOrEmpty()) {
            Text(text = "Recent searches")
        } else {
            LazyColumn {
                items(searchResults) { result ->
                    Text(text = result.title)
                }
            }
        }
    }
}

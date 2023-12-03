package com.migvidal.viwiki2.ui.screens.search_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.network.search.Query
import com.migvidal.viwiki2.ui.components.CustomCard
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SearchScreen(
    searchResults: List<Query.Search>?,
    onSearchClicked: (query: String) -> Unit,
    onResultClicked: (id: Int) -> Unit,
) {

    Column {
        // search bar
        var query: String by rememberSaveable { mutableStateOf("") }
        var active: Boolean by rememberSaveable { mutableStateOf(false) }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { onSearchClicked.invoke(query) },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search for articles")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (active && query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear search")
                    }
                } else if (active) {
                    Button(
                        onClick = { active = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(text = "Close")
                    }
                }
            }
        ) {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                if (searchResults.isNullOrEmpty()) {
                    SectionHeading(text = "Recent searches")
                } else {
                    LazyColumn {
                        itemsIndexed(
                            items = searchResults,
                            key = { _, searchResult -> searchResult.pageId }) { index, result ->
                            val cardModifier = if (index == 0) {
                                Modifier.padding(top = 16.dp)
                            } else {
                                Modifier
                            }
                            CustomCard(
                                modifier = cardModifier.padding(4.dp),
                                onClick = {
                                    onResultClicked.invoke(result.pageId)
                                }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    text = result.title
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

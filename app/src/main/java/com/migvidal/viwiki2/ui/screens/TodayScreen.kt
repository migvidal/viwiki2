package com.migvidal.viwiki2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migvidal.viwiki2.ui.ViWikiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@Destination()
@RootNavGraph(start = true)
fun TodayScreen() {
    val viewModel: ViWikiViewModel = viewModel(factory = ViWikiViewModel.Factory)
    Column {
        val data = viewModel.dayData.collectAsState(initial = null).value
        if (data?.databaseFeaturedArticle == null) {
            Button(onClick = { viewModel.refreshDataFromRepository() }) {
                Text(text = "Show data")
            }
        } else {
            LazyColumn(state = rememberLazyListState()) {
                item {
                    Text(text = "Today's Featured Article".uppercase(), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Light)
                    Text(
                        text = data.databaseFeaturedArticle.normalizedTitle,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(text = data.databaseFeaturedArticle.description)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "MOST READ ARTICLES".uppercase(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                items(data.databaseMostReadArticles ?: return@LazyColumn) { article ->
                    Text(
                        text = article.normalizedTitle,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = article.extract
                    )
                }
            }
        }
    }
}
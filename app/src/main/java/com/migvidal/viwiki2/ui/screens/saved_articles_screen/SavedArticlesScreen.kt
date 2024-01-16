package com.migvidal.viwiki2.ui.screens.saved_articles_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SavedArticlesScreen(modifier: Modifier = Modifier, viewModel: SavedArticlesViewModel) {
    val savedArticles = viewModel.savedArticles.collectAsState(initial = null).value
    savedArticles ?: return
    LazyColumn {
        items(savedArticles) { article ->
            article?.normalizedTitle?.let {
                Text(text = it)
            }
        }
    }
}
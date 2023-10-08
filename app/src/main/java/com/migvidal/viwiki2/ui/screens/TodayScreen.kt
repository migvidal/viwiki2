package com.migvidal.viwiki2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
        Text(text = "Today", style = MaterialTheme.typography.headlineLarge)
        val data = viewModel.dayData.collectAsState(initial = null).value
        if (data?.databaseFeaturedArticle == null) {
            Button(onClick = { viewModel.refreshDataFromRepository() }) {
                Text(text = "Show data")
            }
        } else {
            Text(text = data.databaseFeaturedArticle.normalizedTitle, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = data.databaseFeaturedArticle.description)
        }
    }
}
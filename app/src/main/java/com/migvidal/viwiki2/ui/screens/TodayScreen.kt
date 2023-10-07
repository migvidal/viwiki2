package com.migvidal.viwiki2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migvidal.viwiki2.ui.ViWikiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@Destination()
@RootNavGraph(start = true)
fun TodayScreen() {
    val viewModel = viewModel(modelClass = ViWikiViewModel::class.java)
    Column {
        Text(text = "Today", style = MaterialTheme.typography.headlineLarge)
        val data = viewModel.dayData.collectAsState(initial = null).value ?: return
        Text(text = data.featuredArticle.normalizedTitle)
    }
}
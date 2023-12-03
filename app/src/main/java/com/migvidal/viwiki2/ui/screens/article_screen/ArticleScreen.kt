package com.migvidal.viwiki2.ui.screens.article_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.migvidal.viwiki2.data.network.article.NetworkQuery
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ArticleScreen(modifier: Modifier = Modifier, article: NetworkQuery.NetworkArticle) {
    Column {
        Text(text = article.title)
        Text(text = article.extract)
    }
}
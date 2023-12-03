package com.migvidal.viwiki2.ui.screens.article_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ArticleScreen(
    modifier: Modifier = Modifier,
    articleId: Int,
    viewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory)
) {
    viewModel.refreshArticleDataFromRepository(articleId)
    val articleData = viewModel.articleData.collectAsState().value
    articleData.query?.pages?.first()?.let { article ->
        Column(modifier = modifier) {
            Text(text = article.title)
            Text(text = article.extract)
        }
    }
}
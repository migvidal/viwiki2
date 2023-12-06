package com.migvidal.viwiki2.ui.screens.article_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ArticleScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleViewModel,
    articleId: Int,
) {
    viewModel.refreshArticleDataFromRepository(articleId)
    val articleData = viewModel.articleData.collectAsState().value
    articleData.query?.pages?.first()?.let { article ->
        LazyColumn(modifier = modifier) {
            item {
                Text(text = article.title, style = MaterialTheme.typography.displayMedium)
                article.thumbnail?.let {
                    val aspectRatio = it.width / it.height.toFloat()
                    CustomAsyncImage(model = it.source, aspectRatio = aspectRatio)
                }
                Text(text = article.extract)
            }
        }
    }
}
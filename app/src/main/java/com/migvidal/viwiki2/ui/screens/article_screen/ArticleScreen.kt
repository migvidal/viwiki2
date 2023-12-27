package com.migvidal.viwiki2.ui.screens.article_screen

import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.migvidal.viwiki2.data.network.article.ArticleResponseModel
import com.migvidal.viwiki2.data.repository.Repository
import com.migvidal.viwiki2.ui.CustomTransitions
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ArticleScreenNavWrapper(articleId: Int) {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(style = CustomTransitions::class)
fun AnimatedVisibilityScope.ArticleScreen(
    modifier: Modifier = Modifier,
    articleData: ArticleResponseModel,
    articleStatus: Repository.Status,
) {
    articleData.query?.pages?.first()?.let { article ->
        LazyColumn(modifier = modifier) {
            item {
                if (articleStatus == Repository.Status.Loading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
            item {
                Text(text = article.title, style = MaterialTheme.typography.displayMedium)
                article.original?.let {
                    val aspectRatio = it.width / it.height.toFloat()
                    CustomAsyncImage(model = it.source, aspectRatio = aspectRatio)
                }
                val cssTextColor = if (isSystemInDarkTheme()) "white" else "black"
                AndroidView(factory = { context ->
                    val webView = WebView(context)
                    webView.setBackgroundColor(Color.Transparent.toArgb())
                    webView
                }, update = { view ->
                    val htmlString = "<html><head>" +
                            "<style type=\"text/css\">body { color: $cssTextColor; }</style>" +
                            "</head>" +
                            "<body>" +
                            article.extract +
                            "</body></html>"
                    view.loadData(htmlString, "text/html; charset=utf-8", "UTF-8")
                })
            }
        }
    }
}
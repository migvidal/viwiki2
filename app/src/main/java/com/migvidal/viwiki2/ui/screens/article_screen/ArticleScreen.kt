package com.migvidal.viwiki2.ui.screens.article_screen

import android.webkit.WebView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
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
                article.original?.let {
                    val aspectRatio = it.width / it.height.toFloat()
                    CustomAsyncImage(model = it.source, aspectRatio = aspectRatio)
                }
                //Text(text = article.extract)
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
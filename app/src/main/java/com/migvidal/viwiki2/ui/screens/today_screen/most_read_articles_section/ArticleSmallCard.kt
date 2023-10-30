package com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.fakeArticles
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.migvidal.viwiki2.ui.components.CustomCard
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme

private val CardTextHorizontalPadding = 16.dp

@Composable
internal fun ArticleSmallCard(
    modifier: Modifier = Modifier,
    article: UiArticle,
    index: Int,
    onClick: () -> Unit,
) {
    CustomCard(
        modifier = modifier.aspectRatio(9 / 4f), onClick = onClick
    ) {
        Row(modifier = Modifier.weight(1f)) {
            CustomAsyncImage(model = article.thumbnail?.source, aspectRatio = 3 / 4f)
            Column {
                Text(
                    modifier = Modifier.padding(horizontal = CardTextHorizontalPadding),
                    text = article.normalizedTitle,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = CardTextHorizontalPadding, vertical = 4.dp),
                    text = article.description,
                    maxLines = 3,
                )

                val topMostRead = 0..2
                if (index in topMostRead) {
                    article.views?.let {
                        ViewsIndicator(
                            modifier = Modifier.padding(horizontal = CardTextHorizontalPadding, vertical = 2.dp),
                            views = it,
                            index = index
                        )
                    }
                }
            }
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ArticleSmallCardPreview() {
    ViWiki2Theme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ArticleSmallCard(article = fakeArticles.first(), index = 0, onClick = {})
        }
    }
}
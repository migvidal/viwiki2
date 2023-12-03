package com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.ui.UiDayArticle
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge

@Composable
internal fun MostReadArticlesSection(
    mostReadArticles: List<UiDayArticle>, onArticleClicked: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column {
        SectionHeading(text = "Most read articles")
        Box(
            modifier = Modifier
                .wrapContentWidth(unbounded = true)
                .width(screenWidth.dp)
                .withGradientEdge(
                    side = Side.End, backgroundColor = MaterialTheme.colorScheme.background
                )
        ) {
            LazyHorizontalGrid(
                modifier = Modifier.height((8 * 40).dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemsIndexed(
                    items = mostReadArticles,
                ) { index, article ->
                    val lastArticleIndex = mostReadArticles.size - 1
                    ArticleSmallCard(
                        modifier = Modifier
                            .padding(start = if (index in 0..1) 16.dp else 0.dp)
                            .padding(end = if (index in lastArticleIndex - 1..lastArticleIndex) 16.dp else 0.dp),
                        article = article,
                        index = index,
                        onClick = onArticleClicked,
                    )
                }
            }
        }

    }
}


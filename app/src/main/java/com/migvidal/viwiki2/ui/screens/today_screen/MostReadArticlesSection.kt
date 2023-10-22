package com.migvidal.viwiki2.ui.screens.today_screen

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge
import java.util.Locale

@Composable
internal fun MostReadArticlesSection(
    mostReadArticles: List<UiArticle>,
    onArticleClicked: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column {
        SectionHeading(text = "Most read articles")
        Box(
            modifier = Modifier
                .wrapContentWidth(unbounded = true)
                .width(screenWidth.dp)
                .withGradientEdge(
                    side = Side.End,
                    backgroundColor = MaterialTheme.colorScheme.background
                )
        ) {
            LazyHorizontalGrid(
                modifier = Modifier.height(240.dp),
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ArticleSmallCard(
    modifier: Modifier = Modifier,
    article: UiArticle,
    index: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .aspectRatio(9 / 3f),
        shape = RectangleShape,
        onClick = onClick
    ) {
        Row(modifier = Modifier.weight(1f)) {
            CustomAsyncImage(model = article.thumbnail?.source, aspectRatio = 1f)
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = article.normalizedTitle,
                    style = MaterialTheme.typography.titleMedium,
                )
                val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH).apply {
                    groupingSeparator = ' '
                }
                val decimalFormat = DecimalFormat("###,###", formatSymbols).apply {
                    maximumSignificantDigits = 3
                }
                val topMostRead = 0..2
                if (index in topMostRead) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .alpha(0.6f),
                    ) {
                        article.views?.let {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = "Views"
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            val viewsInThousands = it / 1000
                            Text(
                                modifier = Modifier,
                                text = decimalFormat.format(viewsInThousands) + " K",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }

    }
}
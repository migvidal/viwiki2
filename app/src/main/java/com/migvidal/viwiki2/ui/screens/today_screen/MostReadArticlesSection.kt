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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
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
                    Card(
                        modifier = Modifier
                            .aspectRatio(9 / 3f)
                            .padding(start = if (index in 0..1) 16.dp else 0.dp)
                            .padding(end = if (index in lastArticleIndex -1 .. lastArticleIndex) 16.dp else 0.dp),
                        shape = RectangleShape,
                        onClick = onArticleClicked
                    ) {
                        Row(modifier = Modifier.weight(1f)) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .aspectRatio(1f),
                                model = article.thumbnail?.source,
                                contentScale = ContentScale.FillWidth,
                                contentDescription = null,
                                loading = {
                                    Surface(color = MaterialTheme.colorScheme.onBackground) {}
                                },
                                error = {
                                    Surface(color = MaterialTheme.colorScheme.onBackground) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = "Error loading image"
                                            )
                                        }
                                    }
                                })
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
                                                text = decimalFormat.format(viewsInThousands) + " K views",
                                                fontWeight = FontWeight.Bold,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

    }
}
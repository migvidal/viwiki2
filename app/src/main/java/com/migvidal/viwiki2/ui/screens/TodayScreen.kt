package com.migvidal.viwiki2.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.database.DayData
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.fakeDayData
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@Destination()
@RootNavGraph(start = true)
fun TodayScreen(dayData: DayData?, onRefreshClicked: () -> Unit) {
    if (dayData == null) {
        Button(onClick = onRefreshClicked) {
            Text(text = "Refresh")
        }
    } else {
        LazyColumn(state = rememberLazyListState()) {
            item {
                SectionHeading("Today's Featured Article")
            }
            item {
                FeaturedActicleSection(
                    featuredArticle = dayData.databaseFeaturedArticle ?: return@item
                )
            }
            item {
                SectionHeading(text = "Most read articles")
            }
            item {
                MostReadArticlesSection(
                    mostReadArticles = dayData.databaseMostReadArticles ?: return@item,
                    onArticleClicked = {}
                )
            }


        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MostReadArticlesSection(
    mostReadArticles: List<DatabaseArticle>,
    onArticleClicked: () -> Unit
) {
    Box(
        modifier = Modifier.withGradientEdge(
            side = Side.End,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    ) {
        LazyHorizontalGrid(
            modifier = Modifier.height(240.dp),
            rows = GridCells.Fixed(3)
        ) {
            items(items = mostReadArticles) {
                Card(modifier = Modifier.padding(8.dp), onClick = onArticleClicked) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        Text(modifier = Modifier.padding(16.dp), text = it.normalizedTitle)
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedActicleSection(featuredArticle: DatabaseFeaturedArticle) {
    Text(
        text = featuredArticle.normalizedTitle,
        style = MaterialTheme.typography.headlineLarge
    )
    Text(text = featuredArticle.description)
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TodayScreenPreview() {
    ViWiki2Theme {
        Surface {
            TodayScreen(dayData = fakeDayData, onRefreshClicked = {})
        }

    }
}
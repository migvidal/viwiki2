package com.migvidal.viwiki2.ui.screens.today_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun MostReadArticlesSection(
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
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items = mostReadArticles) {
                Card(modifier = Modifier.aspectRatio(21 / 9f), onClick = onArticleClicked) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = it.normalizedTitle
                        )
                    }
                }
            }
        }
    }
}
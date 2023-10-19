package com.migvidal.viwiki2.ui.screens.today_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun MostReadArticlesSection(
    mostReadArticles: List<UiArticle>,
    onArticleClicked: () -> Unit
) {
    Column {
        SectionHeading(text = "Most read articles")
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
                    Card(
                        modifier = Modifier.aspectRatio(21 / 9f),
                        shape = RectangleShape,
                        onClick = onArticleClicked
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = it.normalizedTitle,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier.padding(16.dp).alpha(0.5f),
                            text = it.views.toString(),
                            fontWeight = FontWeight.ExtraBold,
                        )

                    }
                }
            }
        }

    }
}
package com.migvidal.viwiki2.ui.screens.today_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.ui.UiOnThisDay
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.migvidal.viwiki2.ui.components.CustomCard
import com.migvidal.viwiki2.ui.components.Side
import com.migvidal.viwiki2.ui.components.withGradientEdge

@Composable
fun OnThisDayYearSection(
    onThisDay: UiOnThisDay,
    onArticleClicked: (articleId: Int) -> Unit
) {
    Column {
        Text(
            text = "On ${onThisDay.year}",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(text = onThisDay.text)
        onThisDay.articles?.let { articles ->
            val screenWidth = LocalConfiguration.current.screenWidthDp
            Box(
                modifier = Modifier
                    .wrapContentWidth(unbounded = true)
                    .width(screenWidth.dp)
                    .withGradientEdge(
                        side = Side.End,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
            ) {
                LazyRow(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(vertical = 16.dp)
                ) {
                    itemsIndexed(items = articles) { index, article ->
                        CustomCard(
                            modifier = Modifier
                                .aspectRatio(7 / 2f)
                                .padding(
                                    end = 4.dp,
                                    start = if (index == 0) 16.dp else 4.dp),
                            onClick = { onArticleClicked.invoke(article.id) }) {
                            Row {
                                CustomAsyncImage(
                                    model = article.thumbnail?.sourceAndId,
                                    aspectRatio = 3 / 4f
                                )
                                article.normalizedTitle?.let {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = it,
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
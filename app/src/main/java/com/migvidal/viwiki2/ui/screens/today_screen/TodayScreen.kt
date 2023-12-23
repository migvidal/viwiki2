package com.migvidal.viwiki2.ui.screens.today_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.fakeDayData
import com.migvidal.viwiki2.data.repository.Repository
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.migvidal.viwiki2.ui.components.CustomCard
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section.MostReadArticlesSection
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@Destination
@RootNavGraph(start = true)
fun TodayScreen(
    dayData: UiDayData?,
    dayDataStatus: Repository.Status?,
    onRefreshClicked: () -> Unit,
    onArticleClicked: (articleId: Int) -> Unit
) {
    when (dayDataStatus) {
        Repository.Status.Error -> Text(text = "Error")
        Repository.Status.Loading -> CircularProgressIndicator()
        Repository.Status.Success -> {
            LazyColumn(state = rememberLazyListState()) {
                item {
                    Button(onClick = onRefreshClicked) {
                        Text(text = "Refresh")
                    }
                }
                if (dayData == null) return@LazyColumn
                dayData.image?.let {
                    item {
                        DayImageSection(it)
                    }
                }
                dayData.featuredArticle?.let {
                    item {
                        FeaturedArticleSection(featuredArticle = it, onArticleClicked = {
                            onArticleClicked.invoke(it.id)
                        })
                    }
                }
                dayData.mostReadArticles?.let {
                    item {
                        MostReadArticlesSection(
                            mostReadArticles = it,
                            onArticleClicked = {
                                onArticleClicked.invoke(it.id)
                            }
                        )
                    }
                }
                dayData.onThisDay?.let { yearContent ->
                    item {
                        SectionHeading(text = "On this day")
                    }
                    items(yearContent) { onThisDay ->
                        Column {
                            Text(
                                text = "On ${onThisDay.year}",
                                style = MaterialTheme.typography.titleLarge,
                            )
                            Text(text = onThisDay.text)
                            onThisDay.articles?.let { articles ->
                                LazyRow(
                                    modifier = Modifier
                                        .height(120.dp)
                                        .padding(vertical = 16.dp)
                                ) {
                                    items(items = articles) { article ->
                                        CustomCard(
                                            modifier = Modifier
                                                .aspectRatio(7 / 2f)
                                                .padding(horizontal = 4.dp),
                                            onClick = { onArticleClicked.invoke(article.id) }) {
                                            Row {
                                                CustomAsyncImage(
                                                    model = article.thumbnail?.sourceAndId,
                                                    aspectRatio = 3 / 4f
                                                )
                                                Text(
                                                    modifier = Modifier.padding(8.dp),
                                                    text = article.normalizedTitle,
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
        null -> {}
    }


}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TodayScreenPreview() {
    ViWiki2Theme {
        Surface {
            TodayScreen(
                dayData = fakeDayData,
                dayDataStatus = Repository.Status.Success,
                onRefreshClicked = {},
                onArticleClicked = {})
        }

    }
}
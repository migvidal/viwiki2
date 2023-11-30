package com.migvidal.viwiki2.ui.screens.today_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.migvidal.viwiki2.data.fakeDayData
import com.migvidal.viwiki2.data.repository.Repository
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section.MostReadArticlesSection
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@Destination
@RootNavGraph(start = true)
fun TodayScreen(dayData: UiDayData?, dayDataStatus: Repository.Status?, onRefreshClicked: () -> Unit) {
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
                FeaturedArticleSection(featuredArticle = it)
            }
        }
        dayData.mostReadArticles?.let {
            item {
                MostReadArticlesSection(
                    mostReadArticles = it,
                    onArticleClicked = {}
                )
            }
        }
        dayData.databaseOnThisDay?.let {
            item {
                SectionHeading(text = "On this day")
            }
            items(it) { onThisDay ->
                Column {
                    Text(text = "On ${onThisDay.year}")
                    Text(text = onThisDay.text)
                }
            }
        }


    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TodayScreenPreview() {
    ViWiki2Theme {
        Surface {
            TodayScreen(dayData = fakeDayData, dayDataStatus = Repository.Status.Success, onRefreshClicked = {})
        }

    }
}
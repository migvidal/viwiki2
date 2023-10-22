package com.migvidal.viwiki2.ui.screens.today_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.fakeUiFeaturedArticle
import com.migvidal.viwiki2.ui.UiFeaturedArticle
import com.migvidal.viwiki2.ui.components.CustomAsyncImage
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme

@Composable
internal fun FeaturedArticleSection(featuredArticle: UiFeaturedArticle) {
    Column {
        SectionHeading(text = "Today's Featured Article")
        Card(shape = RectangleShape) {
            CustomAsyncImage(modifier = Modifier.fillMaxWidth(), model = featuredArticle.thumbnail.source)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = featuredArticle.normalizedTitle,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(text = featuredArticle.description)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun FeaturedArticleSectionPreview() {
    ViWiki2Theme {
        Surface {
            FeaturedArticleSection(fakeUiFeaturedArticle)
        }
    }
}
package com.migvidal.viwiki2.ui.screens.today_screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle

@Composable
internal fun FeaturedActicleSection(featuredArticle: DatabaseFeaturedArticle) {
    Text(
        text = featuredArticle.normalizedTitle,
        style = MaterialTheme.typography.headlineLarge
    )
    Text(text = featuredArticle.description)
    Spacer(modifier = Modifier.height(16.dp))
}
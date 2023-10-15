package com.migvidal.viwiki2.ui.screens.today_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.SubcomposeAsyncImage
import com.migvidal.viwiki2.ui.UiDayImage
import com.migvidal.viwiki2.ui.components.SectionHeading

@Composable
internal fun DayImageSection(uiDayImage: UiDayImage) {
    Column {
        SectionHeading(text = "Image of the day")
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = uiDayImage.thumbnail.source,
            contentDescription = null,
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error loading image"
                )
            })
        Text(text = uiDayImage.description)
    }
}
package com.migvidal.viwiki2.ui.screens.today_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.migvidal.viwiki2.data.fakeUiDayImage
import com.migvidal.viwiki2.ui.UiDayImage
import com.migvidal.viwiki2.ui.components.SectionHeading
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme

@Composable
internal fun DayImageSection(uiDayImage: UiDayImage) {
    Column {
        val aspectRatio = uiDayImage.thumbnail.width / uiDayImage.thumbnail.height.toFloat()
        SectionHeading(text = "Image of the day")
        SubcomposeAsyncImage(
            modifier = Modifier.aspectRatio(aspectRatio).fillMaxWidth(),
            model = uiDayImage.thumbnail.source,
            contentScale = ContentScale.FillWidth,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DayImageSectionPreview() {
    ViWiki2Theme {
        Surface { DayImageSection(fakeUiDayImage) }
    }
}
package com.migvidal.viwiki2.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme

@Composable
fun SectionHeading(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        text = text.uppercase(),
        style = MaterialTheme.typography.titleSmall.copy(letterSpacing = 1.2.sp),
        fontWeight = FontWeight.Light
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SectionHeadingPreview() {
    ViWiki2Theme {
        Surface {
            Column(modifier = Modifier.padding(8.dp)) {
                SectionHeading(text = "This is the heading")
                Text(text = "This is more text")
            }
        }
    }
}
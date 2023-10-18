package com.migvidal.viwiki2.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeading(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        text = text.uppercase(),
        style = MaterialTheme.typography.titleSmall.copy(letterSpacing = 1.2.sp),
        fontWeight = FontWeight.Light
    )
}
package com.migvidal.viwiki2.ui.components

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingSkeleton(modifier: Modifier = Modifier, startColor: Color) {
    val color = remember { Animatable(startColor) }
    val endColor = startColor.copy(alpha = 0.75f)
    LaunchedEffect(Unit) {
        while (true) {
            color.animateTo(endColor, animationSpec = tween(750))
            color.animateTo(startColor, animationSpec = tween(750))
        }
    }
    Box(modifier.fillMaxSize().background(color.value))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun AnimatedBoxPreview() {
    Surface {
        LoadingSkeleton(
            modifier = Modifier.fillMaxSize(),
            startColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}
package com.migvidal.viwiki2.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer


internal fun Modifier.withGradientEdge(side: Side, backgroundColor: Color): Modifier {
    val brush = when (side) {
        Side.Start -> Brush.horizontalGradient(
            startX = 0f,
            endX = Float.POSITIVE_INFINITY,
            colorStops = arrayOf(
                0.1f to Color.Transparent,
                0f to backgroundColor,
            ))
        Side.End -> Brush.horizontalGradient(
            startX = Float.POSITIVE_INFINITY,
            endX = 0f,
            colorStops = arrayOf(
                0f to backgroundColor,
                0.1f to Color.Transparent,
            ))
        Side.Top -> Brush.verticalGradient(
            startY = 0f,
            endY = Float.POSITIVE_INFINITY,
            colorStops = arrayOf(
                0f to backgroundColor,
                0.1f to Color.Transparent,
            ))
        Side.Bottom -> Brush.verticalGradient(
            startY = Float.POSITIVE_INFINITY,
            endY = 0f,
            colorStops = arrayOf(
                0f to backgroundColor,
                0.1f to Color.Transparent,
            )
        )
    }
    return this
        .fillMaxSize()
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()
            drawRect(brush = brush)
        }
}
internal enum class Side {
    Start, End, Top, Bottom
}
package com.migvidal.viwiki2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val cardBorder: BorderStroke
    @Composable get() = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
    )

private val cardElevation: CardElevation
    @Composable get() = CardDefaults.cardElevation(defaultElevation = 4.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        border = cardBorder,
        elevation = cardElevation,
        onClick = onClick,
    ) {
        content()
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        border = cardBorder,
        elevation = cardElevation,
    ) {
        content()
    }
}
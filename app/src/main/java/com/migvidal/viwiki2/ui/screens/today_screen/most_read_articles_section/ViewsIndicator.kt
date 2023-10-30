package com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
internal fun ViewsIndicator(views: Int) {
    Row(
        modifier = Modifier
            .alpha(0.6f),
    ) {
        val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH).apply {
            groupingSeparator = ' '
        }
        val decimalFormat = DecimalFormat("###,###", formatSymbols).apply {
            maximumSignificantDigits = 3
        }
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.RemoveRedEye,
            contentDescription = "Views"
        )
        Spacer(modifier = Modifier.size(4.dp))
        val viewsInThousands = views / 1000
        Text(
            modifier = Modifier,
            text = decimalFormat.format(viewsInThousands) + " K",
            fontWeight = FontWeight.Bold,
        )
    }
}
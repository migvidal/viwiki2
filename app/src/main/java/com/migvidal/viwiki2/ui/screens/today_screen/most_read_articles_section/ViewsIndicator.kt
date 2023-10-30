package com.migvidal.viwiki2.ui.screens.today_screen.most_read_articles_section

import android.content.res.Configuration
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import java.util.Locale

private const val LowEmphasisAlpha = 0.6f

@Composable
internal fun ViewsIndicator(modifier: Modifier = Modifier, views: Int, index: Int) {
    Surface {
        Row(
            modifier = modifier
                .alpha(LowEmphasisAlpha),
            verticalAlignment = Alignment.Bottom,
        ) {
            val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH).apply {
                groupingSeparator = ' '
            }
            val decimalFormat = DecimalFormat("###,###", formatSymbols).apply {
                maximumSignificantDigits = 3
            }
            Text(
                modifier = Modifier.alpha(0.6f),
                text = "#",
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = (index + 1).toString(),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .alpha(0.6f),
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
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ViewsIndicatorPreview() {
    ViWiki2Theme {
        Surface {
            ViewsIndicator(views = 200_000, index = 2)
        }
    }
}
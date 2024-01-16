package com.migvidal.viwiki2

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import com.migvidal.viwiki2.ui.screens.destinations.Destination
import com.migvidal.viwiki2.ui.screens.destinations.SavedArticlesScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.SearchScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.TodayScreenDestination

enum class TopLevelDestination(
    @StringRes val label: Int,
    val icon: ImageVector,
    val destination: Destination,
) {
    Today(
        label = R.string.today,
        icon = Icons.Default.Home,
        destination = TodayScreenDestination
    ),
    Search(
        label = R.string.search,
        icon = Icons.Default.Search,
        destination = SearchScreenDestination
    ),
    SavedArticles(
        label = R.string.saved_articles,
        icon = Icons.Default.SaveAlt,
        destination = SavedArticlesScreenDestination
    ),
    ;

    companion object {
        fun from(navDestination: NavDestination?): TopLevelDestination? =
            entries.find { it.destination.route == navDestination?.route }
    }
}
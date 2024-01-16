package com.migvidal.viwiki2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.migvidal.viwiki2.ui.screens.destinations.ArticleScreenNavWrapperDestination
import com.migvidal.viwiki2.ui.screens.today_screen.TodayViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomTopBar(
    currentTitleRes: Int,
    currentDestination: NavDestination?,
    navController: NavHostController,
    onCheckNetwork: () -> Unit,
    dayViewModel: TodayViewModel,
    isArticleSaved: Boolean,
    onSaveArticleClicked: (doSave: Boolean) -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = currentTitleRes))
        },
        navigationIcon = {
            val notInTopLevel = TopLevelDestination.from(currentDestination) == null
            if (notInTopLevel) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
        actions = {
            when (navController.currentDestination?.route) {
                TopLevelDestination.Today.destination.route -> {
                    IconButton(onClick = {
                        onCheckNetwork.invoke()
                        dayViewModel.refreshDataFromRepository()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }

                ArticleScreenNavWrapperDestination.route -> {
                    if (isArticleSaved) {
                        IconButton(onClick = { onSaveArticleClicked.invoke(true) }) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = "Save article"
                            )
                        }
                    } else {
                        IconButton(onClick = { onSaveArticleClicked.invoke(false) }) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Unsave article"
                            )
                        }
                    }
                }
            }
        }
    )
}